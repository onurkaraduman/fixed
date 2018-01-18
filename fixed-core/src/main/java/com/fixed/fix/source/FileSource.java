package com.fixed.fix.source;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class FileSource implements Source {

	public Reader reader;

	protected FileSource(String path) throws FileNotFoundException {
		reader = new FileReader(path);
	}

	@Override
	public Reader getReader() {
		return reader;
	}
}
