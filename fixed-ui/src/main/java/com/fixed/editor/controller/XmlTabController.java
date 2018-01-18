package com.fixed.editor.controller;

import com.fixed.editor.tab.xml.XmlTreeView;
import com.fixed.logging.Logger;
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
            AppController.editor.saveDictionary();
        } catch (IOException e) {
            LOG.error("Error during saving", e);
        }

    }

    public void saveAs(String path) {
        try {
            AppController.editor.saveAsDictionary(path);
        } catch (IOException e) {
            LOG.error("Error during saving", e);
        }
    }

    public void undo() {
        AppController.editor.undoDictionary();
    }

    public void redo() {
        AppController.editor.redoDictionary();
    }
}
