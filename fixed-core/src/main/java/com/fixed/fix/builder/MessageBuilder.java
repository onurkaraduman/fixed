package com.fixed.fix.builder;

import com.fixed.fix.model.LogMessage;
import quickfix.ConfigError;
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

	public List<LogMessage> createLogMessages(List<String> rawMessages, String dictPath) throws ConfigError {
		DataDictionary dictionary = new DataDictionary(dictPath);
		List<LogMessage> logMessages = new ArrayList<>();
		for (String rawMessage : rawMessages) {
			LogMessage logMessage = new LogMessage(0, false, null, rawMessage, dictionary);
			logMessages.add(logMessage);
		}
		return logMessages;
	}
}
