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

package org.datadude;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.datadude.gui.Register;
import org.datadude.security.User;

import java.awt.Font;

public class Login extends JFrame {
	private static final long serialVersionUID = 3013538842330993038L;

	JPanel display;
	JTextField user;
	JPasswordField pass;
	JCheckBox register;
	JButton go;

	static User currUser;

	int textWidth = 70;
	int textHeight = 20;
	int x = 300;

	boolean newuser;

	public static void init(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Login(false);
	}

	public Login(boolean nwusr) {

		setTitle(DataDude.VERSION + " Login Screen");
		System.out.println("\n\nInitializing login frame...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		display = new JPanel();
		display.setLayout(null);

		user = new JTextField("");
		user.setBounds(317, 99, 102, 28);
		pass = new JPasswordField("");
		pass.setBounds(317, 130, 102, 28);

		go = new JButton("Go");
		go.addActionListener(new GO());
		go.setBounds(284, 185, 70, 28);

		display.add(user);
		display.add(pass);
		display.add(go);

		getContentPane().add(display);
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(284, 329, 90, 25);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				Register r = new Register();
				dispose();
			}

		});
		display.add(btnRegister);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 13));
		lblUsername.setBounds(225, 105, 80, 16);
		display.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 13));
		lblPassword.setBounds(225, 136, 80, 16);
		display.add(lblPassword);

		JLabel lblTitle = new JLabel("Login or Register");
		lblTitle.setFont(new Font("Australian Sunrise", Font.PLAIN, 16));
		lblTitle.setBounds(252, 27, 167, 18);
		display.add(lblTitle);
		setVisible(true);
		System.out.println("Completed initializing login frame");

	}

	public static User getUser() {
		return currUser;
	}

	private boolean validate(String user, char[] _pass) {
		currUser = null;
		String pass = new String(_pass);
		System.out.println("Validating user...");
		try {
			FileInputStream fileIn = new FileInputStream(DataDude.getPassLoc()
					+ user + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			currUser = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Wrong Username", "Wrong Data",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!currUser.check(pass)) {
			JOptionPane.showMessageDialog(this, "Wrong Password",
					"Incorrect Validation", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		System.out.println("Completed validating user");
		return true;
	}

	private class GO implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Got old user");
			if (validate(user.getText(), pass.getPassword()) == false) {
				return;
			}

			if (currUser.getUserFolder() == null
					|| currUser.getUserFolder() == "null") {
				System.out.println("User's folder is null");
				System.out.println(System.getProperty("user.home"));
				currUser.setUserFolder(System.getProperty("user.home"));
			}
			dispose();
			System.out.println("Initializing Core Engine..!");
			CoreEngine.init();
		}
	}
}
