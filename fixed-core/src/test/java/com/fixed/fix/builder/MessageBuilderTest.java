package com.fixed.fix.builder;

import com.fixed.fix.model.LogMessage;
import com.fixed.fix.parser.FixMessageParser;
import org.junit.Test;
import quickfix.DataDictionary;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.Assert.*;

public class MessageBuilderTest {
	@Test
	public void createLogMessages() throws Exception {
		MessageBuilder messageBuilder = new MessageBuilder();
		DataDictionary dataDictionary = new DataDictionary(getClass().getResource("FIX44.xml").getPath());
		FixMessageParser parser = new FixMessageParser(new FileInputStream(new File(getClass().getResource("fix1.txt").getPath())));
		List<LogMessage> logMessages = messageBuilder.createLogMessages(parser.parse(), dataDictionary);
		assertFalse(logMessages.isEmpty());
	}

}