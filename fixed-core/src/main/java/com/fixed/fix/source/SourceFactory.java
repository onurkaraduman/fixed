package com.fixed.fix.source;

import java.io.FileNotFoundException;

public class SourceFactory {

	public static Source createFileSource(String path) throws FileNotFoundException {
		return new FileSource(path);
	}

	public static Source createMemorySource(String content) throws FileNotFoundException {
		return new MemorySource(content);
	}
}
