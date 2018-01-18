package com.fixed.fix.source;

import java.io.Reader;
import java.io.StringReader;

public class MemorySource implements Source {

	private Reader reader;

	protected MemorySource(String content) {
		reader = new StringReader(content);
	}

	@Override
	public Reader getReader() {
		return reader;
	}
}
