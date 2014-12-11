package org.datadude;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import org.datadude.gui.Splash;
import org.datadude.security.User;

public class LoginFX extends Application {

	User currUser;
	TextField userTextField;
	PasswordField pwBox;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text sceneTitle = new Text("Login");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actionTarget = new Text();
		grid.add(actionTarget, 1, 6);

		btn.setOnAction(go);

		Scene scene = new Scene(grid, 300, 250);
		// grid.setGridLinesVisible(true);
		stage.setTitle("Welcome to JavaFX!");
		stage.setScene(scene);
		stage.show();
	}

	EventHandler<ActionEvent> go = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			System.out.println("Got old user");
			if (validate(userTextField.getText(), pwBox.getText().toCharArray()) == false) {
				return;
			}

			if (currUser.getUserFolder() == null || currUser.getUserFolder() == "null") {
				System.out.println("User's folder is null");
				currUser.setUserFolder(null);
			}
			System.out.println("Initializing Core Engine..!");
			new Splash();
		}
	};

	private boolean validate(String user, char[] _pass) {
		currUser = null;
		String pass = new String(_pass);
		System.out.println("Validating user...");
		try {
			FileInputStream fileIn = new FileInputStream(DataDude.getPassLoc() + user + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			currUser = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Wrong Username", "Incorrect Validation", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!currUser.check(pass)) {
			JOptionPane.showMessageDialog(null, "Wrong Password", "Incorrect Validation", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		System.out.println("Completed validating user");
		return true;
	}

}
