/*******************************************************************************
 *     DataDude is a data managing application designed to have many types of data in one application
 *     Copyright (C) 2015  Ahmed R. (theTechnoKid)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.datadude;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import org.datadude.gui.RegController;
import org.datadude.gui.Splash;
import org.datadude.security.User;

public class LoginFX extends Application {

	final TextField txtUserName = new TextField();
	final PasswordField pf = new PasswordField();

	final Label lblMessage = new Label();
	public static Scene scene;
	public static Stage primaryStage;

	public static void init(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		primaryStage = stage;

		primaryStage.setTitle("DataDude Login");

		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10, 50, 50, 50));

		// Adding HBox
		HBox hb = new HBox();
		hb.setPadding(new Insets(20, 20, 20, 30));

		// Adding GridPane
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setHgap(5);
		grid.setVgap(5);

		// Implementing Nodes for GridPane
		Label lblUserName = new Label("Username:");

		Label lblPassword = new Label("Password:");

		Button btnLogin = new Button("Login");
		Button btnReg = new Button("Register");

		btnLogin.setCursor(Cursor.HAND);
		btnReg.setCursor(Cursor.HAND);

		btnLogin.setDefaultButton(true);

		lblMessage.setTextFill(Color.ORANGERED);

		// Adding Nodes to GridPane layout
		grid.add(lblUserName, 0, 0);
		grid.add(txtUserName, 1, 0);
		grid.add(lblPassword, 0, 1);
		grid.add(pf, 1, 1);
		grid.add(btnLogin, 2, 0);
		grid.add(btnReg, 2, 1);
		grid.add(lblMessage, 1, 2);

		// Reflection for gridPane
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		grid.setEffect(r);

		// DropShadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);

		// Adding text and DropShadow effect to it
		Text text = new Text("DataDude Login");
		text.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
		text.setEffect(dropShadow);

		// Adding text to HBox
		hb.getChildren().add(text);

		// Add ID's to Nodes
		bp.setId("bp");
		grid.setId("root");
		btnLogin.setId("btnLogin");
		btnReg.setId("btnReg");
		text.setId("text");
		lblUserName.setId("infolabl");
		lblPassword.setId("infolabl");
		lblMessage.setId("message");

		// Action for btnLogin
		btnLogin.setOnAction(go);
		btnReg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
				try {
					FXMLLoader l = new FXMLLoader(getClass().getResource("gui/fxml/Register.fxml"));

					Parent root = l.load();

					RegController r = (RegController) l.getController();
					r.init(stage);

					stage.setResizable(false);
					stage.setTitle("Register");
					Scene s = new Scene(root);
					stage.setScene(s);
					stage.centerOnScreen();
					stage.show();
				} catch (Exception e) {
					DataDude.showError(e);
				}

			}
		});

		// Add HBox and GridPane layout to BorderPane Layout
		bp.setTop(hb);
		bp.setCenter(grid);
		primaryStage.setOnCloseRequest((WindowEvent t) -> {
			Platform.exit();
			System.exit(0);
		});
		// Adding BorderPane to the scene and loading CSS
		scene = new Scene(bp);
		scene.getStylesheets().add(getClass().getResource("gui/styles/login.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("DataDude Login");
		primaryStage.setResizable(false);
		primaryStage.show();

		DataDude.endPreloader();
	}

	EventHandler<ActionEvent> go = (ActionEvent e) -> {
		if (validate(txtUserName.getText(), (pf.getText()).toCharArray()) == false) {
			return;
		}

		if (Login.currUser.getUserFolder() == null) {
			Login.currUser.setUserFolder(null);
		}
		primaryStage.close();
		new Splash();
	};

	private boolean validate(String user, char[] _pass) {
		Login.currUser = null;
		String pass = new String(_pass);
		System.out.println("Validating user...");
		try {
			FileInputStream fileIn = new FileInputStream(DataDude.getPassLoc() + user + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Login.currUser = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			lblMessage.setText("Wrong Username");
			return false;
		}

		if (!Login.currUser.check(pass)) {
			lblMessage.setText("Wrong Password");
			return false;
		}
		System.out.println("Completed validating user");
		return true;
	}

}
