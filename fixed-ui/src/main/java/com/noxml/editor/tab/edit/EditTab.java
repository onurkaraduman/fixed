package com.noxml.editor.tab.edit;

import com.noxml.logging.Logger;
import com.noxml.editor.tab.edit.pane.EditPane;
import com.noxml.editor.tab.xml.XmlTreeCell;

public class EditTab {

    public static Logger LOG = Logger.getLogger(EditTab.class);

    private EditPane editPane;


    public EditTab(EditPane editPane) {
        this.editPane = editPane;

    }

    public void laodCell(XmlTreeCell treeCell) {
        editPane.laodCell(treeCell);
    }

    public void createNewElement() {
        editPane.createNewElement();
    }

    public void addEventHandler(EditHandlerType type, EditHandler editHandler) {
        editPane.addEventHandler(type, editHandler);
    }
}
