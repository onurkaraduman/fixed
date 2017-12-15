package com.noxml.editor.controller;

import com.noxml.editor.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;

    private XmlTabController xmlTabController;

    public void injectXmlTabController(XmlTabController xmlTabController) {
        this.xmlTabController = xmlTabController;
    }

    public void handleOpen() {
        AppController.editor.open();
    }

    public void handleSaveAs() {
        xmlTabController.saveAs("");
    }

    public void handleSave() {
        xmlTabController.save();
    }

    public void handleDonation() {
    }

    public void handleExit() {
        AppController.editor.close();
    }

    public void handleReportProblem() {
        App.hostService.showDocument("https://github.com/onurkaraduman/noxml/issues");
    }

    public void handleAbout() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        try {
            alert.setHeaderText("noxml " + AppController.editor.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        alert.setContentText("Easy xml editor.");

        alert.showAndWait();
    }

    public void handleOnlineManual() {
    }

    public void handleUndo() {
        xmlTabController.undo();
    }

    public void handleRedo() {
        xmlTabController.redo();
    }
}
