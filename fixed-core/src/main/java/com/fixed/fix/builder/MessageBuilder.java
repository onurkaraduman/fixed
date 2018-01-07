package com.fixed.fix.builder;

import com.fixed.fix.model.LogMessage;
import quickfix.DataDictionary;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    public List<LogMessage> createLogMessages(List<String> rawMessages, DataDictionary dataDictionary) {
        List<LogMessage> logMessages = new ArrayList<>();
        for (String rawMessage : rawMessages) {
            LogMessage logMessage = new LogMessage(0, false, null, rawMessage, dataDictionary);
            logMessages.add(logMessage);
        }
        return logMessages;
    }
}
