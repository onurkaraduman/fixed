package com.fixed.editor.tab.fix;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TreeTableView;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.fixed.fix.builder.MessageBuilder;
import com.fixed.fix.model.LogField;
import com.fixed.fix.model.LogGroup;
import com.fixed.fix.model.LogMessage;
import com.fixed.fix.parser.FixMessageParser;
import com.fixed.fix.source.Source;
import com.fixed.fix.source.SourceFactory;
import com.fixed.logging.Logger;

import javafx.scene.control.TreeItem;
import quickfix.ConfigError;
import quickfix.DataDictionary;

/**
 * @author Onur Karaduman
 * @since 02.11.17
 */
public class FixTreeTableView extends TreeTableView {

	public static Logger LOG = Logger.getLogger(FixTreeTableView.class);

	private TreeItem<Object> selectedItem;
	private String fixMessagePath;

	public FixTreeTableView() {
		init();
	}

	private void init() {
		setEditable(true);
	}

	public void loadMessage(String message, Path dictPath) throws IOException, ConfigError {
		Source source = SourceFactory.createMemorySource(message);
		loadMessage(source, dictPath);
	}

	public void loadMessage(Path fixPath, Path dictPath) throws DocumentException, IOException, ConfigError {
		fixMessagePath = fixPath.toAbsolutePath().toString();
		Source fileSource = SourceFactory.createFileSource(fixMessagePath);
		loadMessage(fileSource, dictPath);
	}

	private void loadMessage(Source source, Path dictPath) throws IOException, ConfigError {
		FixMessageParser fixParser = new FixMessageParser(source);
		List<String> parse = fixParser.parse();
		MessageBuilder builder = new MessageBuilder();
		DataDictionary dataDictionary = new DataDictionary(dictPath.toAbsolutePath().toString());
		List<LogMessage> messages = builder.createLogMessages(parse, dataDictionary);
		TreeItem<Object> treeItem = new TreeItem<>(messages.get(0).getLogFields().get(0));
		for (LogMessage logMessage : messages) {
			for (LogField logField : logMessage.getLogFields()) {
				treeItem.getChildren().add(convertToTreeItem(logField));
			}
		}
		setRoot(treeItem);
	}

	/**
	 * Convert tree view to xml
	 *
	 * @return
	 * @throws IOException
	 */
	public String toFixMessage() throws IOException {
		return "";

	}

	public Element getRootElement() {
		return (Element) getRoot().getValue();
	}

	public List<TreeItem> getSelectedItems() {
		return (List<TreeItem>) getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
	}

	public String getFixMessagePath() {
		return fixMessagePath;
	}

	private TreeItem<Object> convertToTreeItem(Object element) {
		TreeItem<Object> treeItem = new TreeItem<>(element);
		if (element instanceof LogField) {
			for (LogGroup logGroup : ((LogField) element).getGroups()) {
				convertToTreeItem(logGroup);
			}
		} else if (element instanceof LogGroup) {
			for (LogField logField : ((LogGroup) element).getFields()) {
				convertToTreeItem(logField);
			}
		}
		return treeItem;
	}
}
