package com.fixed.editor.tab.edit.pane;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TablePane extends GridPane {

    private int maxColumn = 10;
    private int currentRow = 0;
    private int currentColumn = 0;

    @SuppressWarnings("unused")
    public TablePane() {
    }

    public TablePane(int column) {
        maxColumn = column;
    }

    public void addControl(Node control) {
        super.add(control, currentColumn, currentRow);
        if (currentColumn == maxColumn - 1) {
            currentColumn = 0;
            currentRow++;
        } else {
            currentColumn++;
        }
    }

    public void removeRow(Node node) {
        int index = getChildren().indexOf(node);
        if (index > 0) {
            Node node1 = getChildren().get(index - 1);
            Node node2 = getChildren().get(index - 2);
            getChildren().remove(node1);
            getChildren().remove(node2);
            getChildren().remove(node);
        }
    }

    public void removeElement(Node node) {
        getChildren().remove(node);
    }
}
