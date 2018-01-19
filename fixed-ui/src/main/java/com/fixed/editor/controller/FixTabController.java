package com.fixed.editor.controller;

import com.fixed.editor.tab.fix.FixTreeTableView;
import com.fixed.fix.model.FixField;
import com.fixed.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class FixTabController {

	public static Logger LOG = Logger.getLogger(FixTabController.class);

	@FXML
	private FixTreeTableView fixTreeView;

	@FXML
	private TreeTableColumn<FixField, String> tblColumnFieldName;

	@FXML
	private TreeTableColumn<FixField, String> tblColumnTag;

	@FXML
	private TreeTableColumn<FixField, String> tblColumnValue;

	@FXML
	private TreeTableColumn<FixField, String> tblColumnFieldValueName;

	@FXML
	private TreeTableColumn<FixField, String> tblColumnRequired;

	public FixTreeTableView getFixTreeView() {
		return fixTreeView;
	}

	public void initialize() {
		tblColumnFieldName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
		tblColumnTag.setCellValueFactory(new TreeItemPropertyValueFactory<>("tag"));
		tblColumnValue.setCellValueFactory(new TreeItemPropertyValueFactory<>("value"));
		tblColumnFieldValueName.setCellValueFactory(new TreeItemPropertyValueFactory<>("valueName"));
		tblColumnRequired.setCellValueFactory(new TreeItemPropertyValueFactory<>("required"));
	}
}
