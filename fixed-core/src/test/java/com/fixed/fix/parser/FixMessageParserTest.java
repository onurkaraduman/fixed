package com.fixed.fix.parser;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class FixMessageParserTest {

	@Test
	public void parse() throws Exception {
		InputStream inputStream = new FileInputStream(new File(getClass().getResource("fix1.txt").getPath()));
		FixMessageParser parser = new FixMessageParser(inputStream);
		List<String> parse = parser.parse();
		System.out.println(parse.toString());
	}

}