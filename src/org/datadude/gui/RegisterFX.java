package org.datadude.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterFX extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader l = new FXMLLoader(getClass().getResource("fxml/Register.fxml"));
		
		Parent root = l.load();
		
		RegController r = (RegController) l.getController();
		r.init(stage);

		stage.setResizable(false);

		Scene s = new Scene(root);
		stage.setScene(s);
		stage.show();
	}

}
