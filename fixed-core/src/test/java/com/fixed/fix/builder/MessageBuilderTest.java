package com.fixed.fix.builder;

import com.fixed.fix.model.LogMessage;
import com.fixed.fix.parser.FixMessageParser;
import com.fixed.fix.source.FileSource;
import com.fixed.fix.source.Source;
import com.fixed.fix.source.SourceFactory;
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
		Source source = SourceFactory.createFileSource(getClass().getResource("fix1.txt").getPath());
		FixMessageParser parser = new FixMessageParser(source);
		List<LogMessage> logMessages = messageBuilder.createLogMessages(parser.parse(), dataDictionary);
		assertFalse(logMessages.isEmpty());
	}
}