package org.dataman;

import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import org.dataman.security.User;

public class Login {

	JFrame f;
	JPanel display;
	JTextField user;
	JPasswordField pass;
	JCheckBox register;
	JButton go;

	static User currUser;

	int textWidth = 70;
	int textHeight = 20;
	int x = 300;

	public static final String VERSION = "DataMan Beta 0.1";

	boolean newuser;

	public static void init(String[] args) {
		new Login();
	}

	public Login() {

		f = new JFrame(VERSION + " Login Screen");
		System.out.println("\n\nInitializing login frame...");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 400);
		f.setResizable(false);
		display = new JPanel();
		display.setLayout(null);

		user = new JTextField("username");
		user.setBounds(x, 100, textWidth, textHeight);
		pass = new JPasswordField("password");
		pass.setBounds(x, 130, textWidth, textHeight);

		register = new JCheckBox("New?");
		register.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (register.isSelected())
					newuser = true;
				if (!register.isSelected())
					newuser = false;
			}
		});
		register.setSelected(true);
		register.setBounds(x, 150, 70, 20);

		go = new JButton("Go");
		go.addActionListener(new GO());
		go.setBounds(x, 185, 70, 20);

		display.add(user);
		display.add(pass);
		display.add(register);
		display.add(go);

		f.add(display);
		f.setVisible(true);
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
			FileInputStream fileIn = new FileInputStream("res/pass/" + user
					+ ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			currUser = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(f, "Wrong Username", "Wrong Data",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!currUser.check(pass)) {
			JOptionPane.showMessageDialog(f, "Wrong Password",
					"Incorrect Validation", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		System.out.println("Completed validating user");
		return true;
	}

	private class GO implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (newuser) {
				System.out.println("Got new user");
				try {
					currUser = new User(user.getText(), pass.getPassword());
					currUser.encrypt();
					currUser.save();
				} catch (IOException i) {
					i.printStackTrace();
				}
			} else {
				System.out.println("Got old user");
				if (validate(user.getText(), pass.getPassword()) == false) {
					return;
				}

				if (currUser.getUserFolder() == null || currUser.getUserFolder() == "null") {
					System.out.println("User's folder is null");
					System.out.println(System.getProperty("user.home"));
					currUser.setUserFolder(System.getProperty("user.home"));
				}
			}
			f.dispose();
			System.out.println("Initializing Core Engine..!");
			CoreEngine.init();
		}
	}
}
