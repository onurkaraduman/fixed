package com.fixed.editor.controller;

import com.fixed.editor.App;
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
		AppController.editor.openDictionary();
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
		App.hostService.showDocument("https://github.com/onurkaraduman/fixed/issues");
	}

	public void handleAbout() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About");
		try {
			alert.setHeaderText("fixed " + AppController.editor.getVersion());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		alert.setContentText("Fix Messages Manager");

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
