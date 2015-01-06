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
package org.datadude.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import org.datadude.DataDude;
import org.datadude.LoginFX;
import org.datadude.security.User;

public class RegController implements Initializable {

	@FXML
	public TextField displayName;

	@FXML
	public TextField username;

	@FXML
	public PasswordField password;

	private Stage thisStage;

	@Override
	public void initialize(URL url, ResourceBundle arg1) {
	}

	public void init(Stage s) {
		thisStage = s;
	}

	public void doCancel ( ActionEvent e ) {
		if (LoginFX.primaryStage != null) {
			// Close this stage
			thisStage.close();
			// Setting original scene
			LoginFX.primaryStage.setScene(LoginFX.scene);
			// Showing the scene
			LoginFX.primaryStage.show();
		} else {
			DataDude.showError(new IllegalStateException("LoginFX's primary "
					+ "stage is null"));
		}
	}
	
	public void doReg(ActionEvent e) {
		System.out.println("Got new user");
		User currUser;
		try {
			File f = new File(DataDude.getPassLoc() + username.getText() + ".ser");
			if (!f.exists()) {
				// f.getParentFile().mkdirs();
				f.createNewFile();
			} else {
				JOptionPane.showMessageDialog(null, "There is already a user with that name.");
				return;
			}
			currUser = new User(username.getText(), (password.getText()).toCharArray());
			currUser.setName(displayName.getText());
			currUser.encrypt();
			currUser.save();
			thisStage.show();
			thisStage.close();
			if (LoginFX.primaryStage != null) {
				// Close this stage
				thisStage.close();
				// Setting original scene
				LoginFX.primaryStage.setScene(LoginFX.scene);
				// Showing the scene
				LoginFX.primaryStage.show();
			} else {
				DataDude.showError(new IllegalStateException("LoginFX's primary "
						+ "stage is null"));
			}
			thisStage = null;
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
