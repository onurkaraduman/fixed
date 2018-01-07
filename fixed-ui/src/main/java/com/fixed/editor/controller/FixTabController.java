package com.fixed.editor.controller;

import com.fixed.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableView;

public class FixTabController {

    public static Logger LOG = Logger.getLogger(FixTabController.class);

    @FXML
    private TreeTableView treeTableView;

    public TreeTableView getTreeTableView() {
        return treeTableView;
    }
}
