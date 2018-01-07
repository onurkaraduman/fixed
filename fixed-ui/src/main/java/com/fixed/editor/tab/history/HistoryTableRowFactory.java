package com.fixed.editor.tab.history;

import com.fixed.editor.history.XmlEditorMemento;
import com.fixed.editor.tab.text.TextTab;
import com.fixed.editor.tab.xml.XmlTab;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.IOException;

public class HistoryTableRowFactory<T> implements Callback<TableView<T>, TableRow<T>> {

    private XmlTab xmlTab;
    private TextTab textTab;

    public HistoryTableRowFactory(XmlTab xmlTab, TextTab textTab) {
        this.xmlTab = xmlTab;
        this.textTab = textTab;
    }

    @Override
    public TableRow<T> call(TableView<T> param) {

        final TableRow<T> row = new TableRow<>();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem menuItem = new MenuItem("Go to state");
        menuItem.setOnAction(event -> {
            xmlTab.setState((XmlEditorMemento) row.getItem());
            try {
                textTab.loadXml(xmlTab.toXml());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        contextMenu.getItems().add(menuItem);

        row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));

        return row;
    }
}
