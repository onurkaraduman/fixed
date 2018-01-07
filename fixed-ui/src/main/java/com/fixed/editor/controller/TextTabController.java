package com.fixed.editor.controller;

import com.fixed.editor.tab.text.pane.TextPane;
import javafx.fxml.FXML;


public class TextTabController {
    @FXML
    private TextPane txtPane;

    public TextPane getTxtPane() {
        return txtPane;
    }
}
