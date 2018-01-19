package com.fixed.editor.tab.fix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fixed.fix.source.Source;
import com.fixed.fix.source.SourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import quickfix.ConfigError;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class FixTab {
	private static final String DEFAULT_DICTIONARY_PATH = "fixed-ui/src/main/resources/com/fixed/editor/tab/fix/FIX44.xml";

	private FixTreeTableView fixTreeView;

	public FixTab(FixTreeTableView fixTreeView) {
		this.fixTreeView = fixTreeView;
	}

	private void init() {

	}

	public void update(String messages) {

	}

	public void loadMessage(String message) throws IOException, ConfigError {
		loadMessage(message, null);
	}

	public void loadMessage(String message, Path path) throws IOException, ConfigError {
		Source messageSource = SourceFactory.createMemorySource(message);
		Source dictionarySource = null;
		if (path == null) {
			dictionarySource = SourceFactory.createFileSource(DEFAULT_DICTIONARY_PATH);
		} else {
			dictionarySource = SourceFactory.createFileSource(path.toAbsolutePath().toString());
		}
		fixTreeView.loadMessage(messageSource, dictionarySource);
	}

	public void loadMessageWithDict(String message, String dictionary) throws IOException, ConfigError {
		Source messageSource = SourceFactory.createMemorySource(message);
		Source dictionarySource = null;
		if (dictionary == null) {
			dictionarySource = SourceFactory.createFileSource(DEFAULT_DICTIONARY_PATH);
		} else {
			dictionarySource = SourceFactory.createMemorySource(dictionary);
		}
		fixTreeView.loadMessage(messageSource, dictionarySource);
	}

	public void loadMessageFromPath(Path fixPath, Path dictPath) throws IOException, ConfigError, DocumentException {
		Source messageSource = SourceFactory.createFileSource(fixPath.toAbsolutePath().toString());
		Source dictionarySource = null;
		if (dictPath == null) {
			dictionarySource = SourceFactory.createFileSource(DEFAULT_DICTIONARY_PATH);
		} else {
			dictionarySource = SourceFactory.createMemorySource(dictPath.toAbsolutePath().toString());
		}
		fixTreeView.loadMessage(messageSource, dictionarySource);
	}

}
