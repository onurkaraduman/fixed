package com.fixed.fix.model;

import com.fixed.fix.util.FixMessageHelper;
import quickfix.*;
import quickfix.field.MsgType;

import java.util.*;

public class LogMessage {

    public static final char DEFAULT_DELIMETER = '|';
    public static final char SOH_DELIMETER = (char) 0x01;


    private final int messageIndex;
    private final SessionID sessionId;
    private final boolean incoming;
    private final String rawMessage;
    private final String messageTypeName;
    private final Date sendingTime;
    private final DataDictionary dictionary;

    private boolean isValid;

    public LogMessage(int messageIndex, boolean incoming, SessionID sessionId, String rawMessage, DataDictionary dictionary) {
        this.messageIndex = messageIndex;

        this.dictionary = dictionary;
        this.rawMessage = rawMessage.replace(SOH_DELIMETER, DEFAULT_DELIMETER);
        this.sessionId = sessionId;
        this.incoming = incoming;

        sendingTime = lookupSendingTime();
        messageTypeName = lookupMessageTypeName();
    }

    public List<LogField> getLogFields() {
        Message message = createMessage();
        List<LogField> logFields = new ArrayList<>();
        if (message != null) {
            Map<Integer, Field> fields = getAllFields(message);

            for (Integer fieldTag : fields.keySet()) {
                LogField logField = createLogField(message, fields.get(fieldTag));
                logFields.add(logField);
            }
        }
        return logFields;
    }

    private Map<Integer, Field> getAllFields(Message message) {
        Map<Integer, Field> allFields = new HashMap<>();

        Iterator<Field<?>> iterator = message.getHeader().iterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            allFields.put(field.getTag(), field);
        }


        iterator = message.iterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            allFields.put(field.getTag(), field);
        }

        iterator = message.getTrailer().iterator();
        while (iterator.hasNext()) {
            Field field = (Field) iterator.next();
            allFields.put(field.getTag(), field);
        }

        return allFields;
    }

    private LogField createLogField(Message message, Field field) {

        MsgType messageType = getMessageType(message);
        String messageTypeValue = messageType.getValue();


        LogField logField = LogField.creteLogField(messageType, field, dictionary);

        addGroup(message, dictionary, field, logField, messageType, messageTypeValue);

        return logField;
    }


    private MsgType getMessageType(Message message) {
        try {
            return (MsgType) message.getHeader().getField(new MsgType());
        } catch (FieldNotFound fieldNotFound) {
            throw new RuntimeException(fieldNotFound);
        }
    }

    private void addGroup(Message message, DataDictionary dataDictionary, Field field, LogField logField, MsgType msgType, String msgTypeValue) {
        DataDictionary.GroupInfo groupInfo = dataDictionary.getGroup(msgTypeValue, field.getTag());
        if (groupInfo != null) {

            int delimiterField = groupInfo.getDelimiterField();
            Group group = new Group(field.getTag(), delimiterField);
            int numOfGroup = Integer.valueOf((String) field.getObject());

            for (int i = 0; i < numOfGroup; i++) {
                LogGroup logGroup = new LogGroup(msgType, field, dictionary);
                try {
                    message.getGroup(i + 1, group);
                    Iterator<Field<?>> iterator = group.iterator();
                    while (iterator.hasNext()) {
                        Field groupField = iterator.next();
                        LogField logGroupField = LogField.creteLogField(msgType, groupField, dataDictionary);
                        logGroup.addField(logGroupField);
                    }

                } catch (FieldNotFound fieldNotFound) {
                    fieldNotFound.printStackTrace();
                }
                logField.addGroup(logGroup);
            }
        }
    }

    private Message createMessage() {
        try {
            return new Message(rawMessage, dictionary, true);
        } catch (InvalidMessage invalidMessage) {
            invalidMessage.printStackTrace();
        }
        return null;
    }

    private Date lookupSendingTime() {
        try {
            return FixMessageHelper.getSendingTime(rawMessage, DEFAULT_DELIMETER);
        } catch (FieldConvertError fieldConvertError) {
            isValid = false;
            return null;
        }
    }

    private String lookupMessageTypeName() {
        String messageTypeValue = FixMessageHelper.getMessageType(rawMessage, DEFAULT_DELIMETER);

        if (messageTypeValue == null) {
            return null;
        }
        return dictionary.getValueName(MsgType.FIELD, messageTypeValue);
    }
}
