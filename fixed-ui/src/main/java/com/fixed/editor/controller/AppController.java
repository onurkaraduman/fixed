package com.fixed.editor.controller;

import java.io.IOException;

import org.dom4j.DocumentException;

import com.fixed.editor.App;
import com.fixed.editor.FixEditor;

import javafx.fxml.FXML;

/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class AppController {

	@FXML
	private HistoryTabController historyTabController;

	@FXML
	private XmlTabController xmlTabController;

	@FXML
	private EditTabController editTabController;

	@FXML
	private MenuBarController menuController;

	@FXML
	private DictionaryTabController dictionaryTabController;

	@FXML
	private LogTabController logTabController;

	@FXML
	private FixTabController fixTabController;

	@FXML
	private FixMessageTabController fixMessageTabController;

	public static FixEditor editor;

	@FXML
	private void initialize() throws DocumentException, IOException {
		String path = null;
		if (App.args != null && App.args.length > 0) {
			path = App.args[0];
		}
		editor = new FixEditor(path, xmlTabController.getTreeView(), editTabController.getGpEdit(), historyTabController.getTblHistory(),
				dictionaryTabController.getTxtPane(), logTabController.getTblLog(), fixTabController.getFixTreeView(), null,
				fixMessageTabController.getTxtPane());
		menuController.injectXmlTabController(xmlTabController);
	}
}
