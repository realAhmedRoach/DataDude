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
	
	public void doReg(ActionEvent e){
		System.out.println("Got new user");
		User currUser;
		try {
			File f = new File(DataDude.getPassLoc() + username.getText() + ".ser");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			} else {
				JOptionPane.showMessageDialog(null, "There is already a user with that name.");
			}
			currUser = new User(username.getText(), (password.getText()).toCharArray());
			currUser.setName(displayName.getText());
			currUser.encrypt();
			currUser.save();
			LoginFX.init(null);
			
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
}
