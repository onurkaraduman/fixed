package com.fixed.editor.tab.fix;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.input.ReaderInputStream;
import org.dom4j.Element;

import com.fixed.fix.builder.MessageBuilder;
import com.fixed.fix.model.FixField;
import com.fixed.fix.model.FixGroup;
import com.fixed.fix.model.FixMessage;
import com.fixed.fix.parser.FixMessageParser;
import com.fixed.fix.source.Source;
import com.fixed.logging.Logger;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
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

	public void loadMessage(Source fixmessage, Source dictionary) throws IOException, ConfigError {
		FixMessageParser fixParser = new FixMessageParser(fixmessage);
		List<String> parse = fixParser.parse();
		MessageBuilder builder = new MessageBuilder();
		DataDictionary dataDictionary = new DataDictionary(new ReaderInputStream(dictionary.getReader()));
		List<FixMessage> messages = builder.createLogMessages(parse, dataDictionary);
		TreeItem<Object> rootItem = new TreeItem<>();
		for (FixMessage logMessage : messages) {
			TreeItem<Object> treeItem = new TreeItem<>(logMessage);
			for (FixField logField : logMessage.getLogFields()) {
				treeItem.getChildren().add(convertToTreeItem(logField));
			}
			rootItem.getChildren().add(treeItem);
		}
		setShowRoot(false);
		setRoot(rootItem);
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

		if (element instanceof FixGroup) {
			for (FixField logField : ((FixGroup) element).getFields()) {
				treeItem.getChildren().add(convertToTreeItem(logField));
			}
		} else if (element instanceof FixField) {
			for (FixGroup logGroup : ((FixField) element).getGroups()) {
				treeItem.getChildren().add(convertToTreeItem(logGroup));
			}
		}
		return treeItem;
	}
}
