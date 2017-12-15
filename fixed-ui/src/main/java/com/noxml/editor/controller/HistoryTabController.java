package com.noxml.editor.controller;

import com.noxml.editor.history.XmlEditorMemento;
import com.noxml.editor.tab.history.HistoryTableCellFactory;
import com.noxml.editor.tab.history.HistoryTableRowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoryTabController {


    @FXML
    private TableView tblHistory;

    @FXML
    private TableColumn<XmlEditorMemento, XmlEditorMemento> tblColumnChange;

    @FXML
    private TableColumn<XmlEditorMemento, XmlEditorMemento> tblColumnDate;

    @FXML
    private TableColumn<XmlEditorMemento, XmlEditorMemento> tblColumnId;

    public TableView getTblHistory() {
        return tblHistory;
    }

    public void initialize() {
        tblColumnChange.setCellValueFactory(new PropertyValueFactory<>("changeType"));
        tblColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblColumnChange.setCellFactory(new HistoryTableCellFactory<XmlEditorMemento, XmlEditorMemento>());
        tblColumnDate.setCellFactory(new HistoryTableCellFactory<XmlEditorMemento, XmlEditorMemento>());
        tblColumnId.setCellFactory(new HistoryTableCellFactory<XmlEditorMemento, XmlEditorMemento>());
    }
}
