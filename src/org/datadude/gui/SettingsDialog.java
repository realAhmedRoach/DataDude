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

import javax.swing.*;
import javax.swing.border.*;

import org.datadude.Login;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SettingsDialog extends JDialog {

	private static final long serialVersionUID = 1872L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JTextField txtPass;

	private ActionListener settingsListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			changeSettings();
		}
		
	};
	private JTextField txtFolder;
	
	/**
	 * Launch the application.
	 */
	public static void init() {
		try {
			SettingsDialog dialog = new SettingsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SettingsDialog() {
		setBounds(100, 100, 530, 370);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 50));
		
		JLabel lblChangeSettings = new JLabel("Change Settings");
		lblChangeSettings.setFont(new Font("Arial Black", Font.BOLD, 16));
		contentPanel.add(lblChangeSettings);
		
		JSeparator separator = new JSeparator();
		contentPanel.add(separator);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.RED);
		contentPanel.add(lblUsername);

		txtUsername = new JTextField();
		contentPanel.add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblUserFolder = new JLabel("User Folder");
		lblUserFolder.setForeground(Color.GREEN);
		contentPanel.add(lblUserFolder);
		
		JPanel panel = new JPanel();
		contentPanel.add(panel);
		
		txtFolder = new JTextField();
		panel.add(txtFolder);
		txtFolder.setColumns(10);
		
		JButton btnBrowse = new JButton();
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fs = new JFileChooser();
				fs.setDialogType(JFileChooser.DIRECTORIES_ONLY);
				fs.showOpenDialog(null);
				if(fs.getSelectedFile() != null)
					txtFolder.setText(fs.getSelectedFile().getAbsolutePath());
			}
		});
		btnBrowse.setIcon(new ImageIcon(SettingsDialog.class.getResource("/javax/swing/plaf/metal/icons/ocean/upFolder.gif")));
		panel.add(btnBrowse);

		JLabel lblPassword = new JLabel("Password (to change settings)");
		lblPassword.setForeground(Color.BLUE);
		contentPanel.add(lblPassword);

		txtPass = new JTextField();
		contentPanel.add(txtPass);
		txtPass.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(settingsListener);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				dispose();
			}
			
		});
		buttonPane.add(cancelButton);

	}

	private void changeSettings() {
		if(Login.getUser().check(txtPass.getText())) {
			if (txtUsername.getText() != "" || txtUsername.getText() != null) {
				File f = new File("res/pass/" + Login.getUser().getUserName());
				f.delete();
				Login.getUser().setUserName(txtUsername.getText());
			}
			if (txtFolder.getText() != "" || txtFolder.getText() != null) {
				Login.getUser().setUserFolder(txtFolder.getText());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Wrong Password", "Couldn't Change Settings", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			Login.getUser().save();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Couldn't Change Settings", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			dispose();
		}
	}

}
