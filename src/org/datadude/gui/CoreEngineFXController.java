package org.datadude.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.datadude.DataDude;
import org.datadude.Login;
import org.datadude.chat.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.*;

public class CoreEngineFXController implements Initializable{

	@FXML
	private Button newB;
	
	@FXML
	private Button filesB;
	
	@FXML
	private Button chatB;
	
	@FXML
	private Button serverB;
	
	@FXML
	private Button settingsB;
	
	@FXML
	private Button quitB;
	
	@FXML
	private MenuItem newM;
	
	@FXML
	private MenuItem settingsM;
	
	@FXML
	private MenuItem quitM;
	
	EventHandler<ActionEvent> newEv = (ActionEvent e) -> NewDialog.init();
	
	EventHandler<ActionEvent> filesEv = (ActionEvent event) -> {
		try {
			Desktop.getDesktop().open(new File(Login.getUser().getUserFolder()));
		} catch (IOException ioe) {
			/* WTF? */
		}	
	};
	
	EventHandler<ActionEvent> chatEv = (ActionEvent e) -> new ClientGUI("", 1500);
	
	EventHandler<ActionEvent> serverEv = (ActionEvent e) -> new ServerGUI(1500);
	
	EventHandler<ActionEvent> settingsEv = (ActionEvent e) -> SettingsDialog.init();
				
	EventHandler<ActionEvent> quitEv = (ActionEvent e) -> {
		DataDude.exit();
		System.exit(0);
	};
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		// Buttons
		newB.setOnAction(newEv);
		filesB.setOnAction(filesEv);
		chatB.setOnAction(chatEv);
		serverB.setOnAction(serverEv);
		settingsB.setOnAction(settingsEv);
		quitB.setOnAction(quitEv);
		
		// Menu Items
		newM.setOnAction(newEv);
		settingsM.setOnAction(settingsEv);
		quitM.setOnAction(quitEv);
	}

}
