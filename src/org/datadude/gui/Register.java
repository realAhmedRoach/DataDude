/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.datadude.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import org.datadude.DataDude;
import org.datadude.Login;
import org.datadude.security.User;






import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class Register extends JDialog {

	private static final long serialVersionUID = 8864236513585958332L;

	private final JPanel contentPanel = new JPanel();

	private ActionListener okListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Got new user");
			User currUser;
			try {
				File f = new File(DataDude.getPassLoc()+user.getText()+".ser");
				f.createNewFile();
				currUser = new User(user.getText(), pass.getPassword());
				currUser.setName(txtName.getText());
				currUser.encrypt();
				currUser.save();
				@SuppressWarnings("unused")
				Login l = new Login(true);
				dispose();
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		
	};
	private JTextField user;
	private JPasswordField pass;
	private JTextField txtName;

	/**
	 * Create the dialog.
	 */
	public Register() {
		setTitle("Register to DataMan");
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDisplayName = new JLabel("Display Name");
		contentPanel.add(lblDisplayName);
		
		txtName = new JTextField();
		contentPanel.add(txtName);
		txtName.setColumns(10);
		{
			JLabel lblUsername = new JLabel("Username");
			contentPanel.add(lblUsername);
		}
		{
			user = new JTextField();
			contentPanel.add(user);
			user.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword);
		}
		
		pass = new JPasswordField();
		contentPanel.add(pass);
		
		JLabel label = new JLabel("");
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(okListener);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
