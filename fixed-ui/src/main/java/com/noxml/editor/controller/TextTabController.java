package com.noxml.editor.controller;

import com.noxml.editor.tab.text.pane.TextPane;
import javafx.fxml.FXML;


public class TextTabController {
    @FXML
    private TextPane txtPane;

    public TextPane getTxtPane() {
        return txtPane;
    }
}
