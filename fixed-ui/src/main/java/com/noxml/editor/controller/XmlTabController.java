package com.noxml.editor.controller;

import com.noxml.editor.tab.xml.XmlTreeView;
import com.noxml.logging.Logger;
import javafx.fxml.FXML;

import java.io.IOException;

public class XmlTabController {

    public static Logger LOG = Logger.getLogger(XmlTabController.class);

    @FXML
    private XmlTreeView treeView;

    public XmlTreeView getTreeView() {
        return treeView;
    }

    public void save() {
        try {
            AppController.editor.save();
        } catch (IOException e) {
            LOG.error("Error during saving", e);
        }

    }

    public void saveAs(String path) {
        try {
            AppController.editor.saveAs(path);
        } catch (IOException e) {
            LOG.error("Error during saving", e);
        }
    }

    public void undo() {
        AppController.editor.undo();
    }

    public void redo() {
        AppController.editor.redo();
    }
}
