package com.fixed.fix.parser;

import java.util.List;

import org.junit.Test;

import com.fixed.fix.source.Source;
import com.fixed.fix.source.SourceFactory;

public class FixMessageParserTest {

	@Test
	public void parse() throws Exception {
		Source source = SourceFactory.createFileSource(getClass().getResource("fix1.txt").getPath());
		FixMessageParser parser = new FixMessageParser(source);
		List<String> parse = parser.parse();
		System.out.println(parse.toString());
	}

}