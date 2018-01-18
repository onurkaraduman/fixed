package com.fixed.editor.controller;

import com.fixed.editor.tab.fix.FixTreeTableView;
import com.fixed.editor.tab.log.LogTableCellFactory;
import com.fixed.fix.model.LogField;
import com.fixed.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class FixTabController {

	public static Logger LOG = Logger.getLogger(FixTabController.class);

	@FXML
	private FixTreeTableView fixTreeView;

	@FXML
	private TreeTableColumn<LogField, String> tblColumnFieldName;

	@FXML
	private TreeTableColumn<LogField, String> tblColumnTag;

	@FXML
	private TreeTableColumn<LogField, String> tblColumnValue;

	@FXML
	private TreeTableColumn<LogField, String> tblColumnFieldValueName;

	@FXML
	private TreeTableColumn<LogField, String> tblColumnRequired;

	public FixTreeTableView getFixTreeView() {
		return fixTreeView;
	}

	public void initialize() {
		tblColumnFieldName.setCellValueFactory(new TreeItemPropertyValueFactory<>("fieldName"));
		tblColumnTag.setCellValueFactory(new TreeItemPropertyValueFactory<>("tag"));
		tblColumnValue.setCellValueFactory(new TreeItemPropertyValueFactory<>("fieldValue"));
		tblColumnFieldValueName.setCellValueFactory(new TreeItemPropertyValueFactory<>("handlerType"));
		tblColumnRequired.setCellValueFactory(new TreeItemPropertyValueFactory<>("required"));
	}
}
