package com.fixed.editor.controller;

import com.fixed.editor.App;
import com.fixed.editor.XmlEditor;
import javafx.fxml.FXML;
import org.dom4j.DocumentException;

import java.io.IOException;


/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class AppController {

    @FXML
    private HistoryTabController historyTabController;

    @FXML
    private XmlTabController xmlTabController;

    @FXML
    private EditTabController editTabController;

    @FXML
    private MenuBarController menuController;

    @FXML
    private TextTabController textTabController;

    @FXML
    private LogTabController logTabController;

    public static XmlEditor editor;

    @FXML
    private void initialize() throws DocumentException, IOException {
        String path = null;
        if (App.args != null && App.args.length > 0) {
            path = App.args[0];
        }
        editor = new XmlEditor(path, xmlTabController.getTreeView(), editTabController.getGpEdit(), historyTabController.getTblHistory(), textTabController.getTxtPane(), logTabController.getTblLog());
        menuController.injectXmlTabController(xmlTabController);
    }
}
