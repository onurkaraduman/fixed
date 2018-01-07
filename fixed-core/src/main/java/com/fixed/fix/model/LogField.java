package com.fixed.fix.model;


import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.FieldType;
import quickfix.field.MsgType;

import java.util.ArrayList;
import java.util.List;

public class LogField {

    private final DataDictionary dataDictionary;
    private final Field field;

    private final FieldType fieldType;
    private final String fieldName;
    private final String fieldValueName;
    private final boolean required;
    private final boolean header;
    private final boolean trailer;
    private List<LogGroup> groups;


    public static LogField creteLogField(MsgType msgType, Field field, DataDictionary dataDictionary) {
        return new LogField(msgType, field, dataDictionary);
    }

    protected LogField(MsgType msgType, Field field, DataDictionary dataDictionary) {
        this.dataDictionary = dataDictionary;
        this.field = field;

        final String messageTypeString = msgType.getValue();
        final int fieldTag = field.getTag();

        fieldType = dataDictionary.getFieldType(fieldTag);
        fieldName = dataDictionary.getFieldName(fieldTag);
        fieldValueName = dataDictionary.getValueName(fieldTag, field.getObject().toString());
        required = dataDictionary.isRequiredField(messageTypeString, fieldTag);


        header = dataDictionary.isHeaderField(fieldTag);
        trailer = !header && dataDictionary.isTrailerField(fieldTag);
    }

    public void addGroup(LogGroup logGroup) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(logGroup);
    }

    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }

    public Field getField() {
        return field;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValueName() {
        return fieldValueName;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isHeader() {
        return header;
    }

    public boolean isTrailer() {
        return trailer;
    }

    public List<LogGroup> getGroups() {
        return groups;
    }
}
