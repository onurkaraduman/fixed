package com.fixed.editor;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class App extends Application {
	public static Parent rootNode;
	public static Stage stage;
	public static String[] args;
	public static HostServicesDelegate hostService;

	public static void main(String[] args) {
		App.args = args;
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/App.fxml"));
		rootNode = fxmlLoader.load();
		Scene scene = new Scene(rootNode, 700, 800);

		stage.setTitle("Fixed");
		stage.setScene(scene);
		stage.show();
		stage.setMaximized(true);
		// stage.setFullScreen(true);
		hostService = HostServicesFactory.getInstance(this);
	}
}
