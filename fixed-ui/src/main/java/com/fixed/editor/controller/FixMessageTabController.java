package com.fixed.editor.controller;

import com.fixed.editor.tab.common.pane.TextPane;
import javafx.fxml.FXML;

public class FixMessageTabController {
    @FXML
    private TextPane txtPane;

    public TextPane getTxtPane() {
        return txtPane;
    }
}
