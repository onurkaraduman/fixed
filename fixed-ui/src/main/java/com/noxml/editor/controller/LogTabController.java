package com.noxml.editor.controller;

import com.noxml.editor.tab.log.LogTableCellFactory;
import com.noxml.logging.LogEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LogTabController {

    @FXML
    private TableView tblLog;


    @FXML
    private TableColumn<LogEvent, String> tblColumnMessage;

    @FXML
    private TableColumn<LogEvent, String> tblColumnDate;

    @FXML
    private TableColumn<LogEvent, String> tblColumnLog;

    @FXML
    private TableColumn<LogEvent, String> tblColumnType;

    public void initialize() {
        tblColumnMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        tblColumnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblColumnLog.setCellValueFactory(new PropertyValueFactory<>("throwable"));
        tblColumnType.setCellValueFactory(new PropertyValueFactory<>("handlerType"));
        tblColumnMessage.setCellFactory(new LogTableCellFactory<>());
        tblColumnDate.setCellFactory(new LogTableCellFactory<>());
        tblColumnLog.setCellFactory(new LogTableCellFactory<>());
        tblColumnType.setCellFactory(new LogTableCellFactory<>());
    }

    public TableView getTblLog() {
        return tblLog;
    }


}
