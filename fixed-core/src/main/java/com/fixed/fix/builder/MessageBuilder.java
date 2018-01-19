package com.fixed.fix.builder;

import com.fixed.fix.model.FixMessage;
import quickfix.ConfigError;
import quickfix.DataDictionary;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

	public List<FixMessage> createLogMessages(List<String> rawMessages, DataDictionary dataDictionary) {
		List<FixMessage> logMessages = new ArrayList<>();
		int messageIndex = 0;
		for (String rawMessage : rawMessages) {
			FixMessage logMessage = new FixMessage(messageIndex++, false, null, rawMessage, dataDictionary);
			logMessages.add(logMessage);
		}
		return logMessages;
	}

	public List<FixMessage> createLogMessages(List<String> rawMessages, String dictPath) throws ConfigError {
		DataDictionary dictionary = new DataDictionary(dictPath);
		List<FixMessage> logMessages = new ArrayList<>();
		int messageIndex = 0;
		for (String rawMessage : rawMessages) {
			FixMessage logMessage = new FixMessage(messageIndex++, false, null, rawMessage, dictionary);
			logMessages.add(logMessage);
		}
		return logMessages;
	}
}
