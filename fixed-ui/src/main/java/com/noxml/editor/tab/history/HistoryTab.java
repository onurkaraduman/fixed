package com.noxml.editor.tab.history;

import com.noxml.editor.history.XmlEditorMemento;
import com.noxml.editor.history.XmlEditorMementoManager;
import com.noxml.editor.tab.text.TextTab;
import com.noxml.editor.tab.xml.XmlTab;
import javafx.scene.control.TableView;

public class HistoryTab {

    private XmlEditorMementoManager mementoManager;

    private TableView tableView;

    public HistoryTab(TableView tableView, XmlEditorMementoManager mementoManager, XmlTab xmlTab, TextTab textTab) {
        this.tableView = tableView;
        this.mementoManager = mementoManager;
        this.mementoManager.addStateChangeHandler(event -> {
            addRow(event.getCurrentState());
        });
        tableView.setRowFactory(new HistoryTableRowFactory<XmlEditorMemento>(xmlTab, textTab));
    }

    private void addRow(XmlEditorMemento memento) {
        tableView.getItems().add(memento);
    }
}

