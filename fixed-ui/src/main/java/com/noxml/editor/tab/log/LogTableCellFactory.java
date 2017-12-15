package com.noxml.editor.tab.log;

import com.noxml.logging.LogHandlerType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LogTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {

        return new TableCell<S, T>() {

            @Override
            protected void updateItem(Object item, boolean empty) {
                if (item != null) {
                    if (item instanceof LogHandlerType) {
                        switch ((LogHandlerType) item) {
                            case INFO:
                                break;
                            case DEBUG:
                                break;
                            case ERROR:
                                getTableRow().setStyle("-fx-background-color:rgba(255,0,0,0.2)");
                                break;
                        }
                    }
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        };
    }
}
