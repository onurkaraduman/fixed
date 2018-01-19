package com.fixed.fix.builder;

import com.fixed.fix.model.FixMessage;
import com.fixed.fix.parser.FixMessageParser;
import com.fixed.fix.source.Source;
import com.fixed.fix.source.SourceFactory;
import org.junit.Test;
import quickfix.DataDictionary;

import java.util.List;

import static org.junit.Assert.*;

public class MessageBuilderTest {
	@Test
	public void createLogMessages() throws Exception {
		MessageBuilder messageBuilder = new MessageBuilder();
		DataDictionary dataDictionary = new DataDictionary(getClass().getResource("FIX44.xml").getPath());
		Source source = SourceFactory.createFileSource(getClass().getResource("fix1.txt").getPath());
		FixMessageParser parser = new FixMessageParser(source);
		List<FixMessage> logMessages = messageBuilder.createLogMessages(parser.parse(), dataDictionary);
		assertFalse(logMessages.isEmpty());
	}
}