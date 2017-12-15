package com.noxml.editor.tab.history;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HistoryTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {

        return new TableCell<S, T>() {

            @Override
            protected void updateItem(Object item, boolean empty) {
                if (item != null) {
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        };
    }
}
