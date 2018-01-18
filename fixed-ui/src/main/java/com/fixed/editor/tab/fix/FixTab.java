package com.fixed.editor.tab.fix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import quickfix.ConfigError;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class FixTab {

	private FixTreeTableView fixTreeView;

	public FixTab(FixTreeTableView fixTreeView) {
		this.fixTreeView = fixTreeView;
	}

	private void init() {

	}

	public void update(String messages) {

	}

	public void loadMessage(String message, String path) throws IOException, ConfigError {
		if(StringUtils.isEmpty(path)){
			fixTreeView.loadMessage(message, Paths.get("fixed-ui/src/main/resources/com/fixed/tree/fix44.xml"));
		}else{
			fixTreeView.loadMessage(message, Paths.get(path));
		}
	}

	public void loadMessage(Path fixPath, Path dictPath) throws IOException, ConfigError, DocumentException {
		fixTreeView.loadMessage(fixPath, dictPath);
	}

}
