package org.dataman;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.dataman.gui.Register;
import org.dataman.security.User;

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

	public static final String VERSION = "DataMan Beta 0.1";

	boolean newuser;

	public static void init(String[] args) {
		new Login();
	}

	public Login() {

		setTitle(VERSION + " Login Screen");
		System.out.println("\n\nInitializing login frame...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setResizable(false);
		display = new JPanel();
		display.setLayout(null);

		user = new JTextField("username");
		user.setBounds(268, 99, 102, 28);
		pass = new JPasswordField("password");
		pass.setBounds(268, 130, 102, 28);

		go = new JButton("Go");
		go.addActionListener(new GO());
		go.setBounds(284, 185, 70, 28);

		display.add(user);
		display.add(pass);
		display.add(go);

		getContentPane().add(display);

		JLabel lblWlcmtxt = new JLabel("Please Login to DataMan");
		lblWlcmtxt.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 17));
		lblWlcmtxt.setBounds(196, 47, 237, 20);
		display.add(lblWlcmtxt);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(268, 156, 90, 28);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Register r = new Register();
				r.initWindow();
				dispose();
			}
			
		});
		display.add(btnRegister);
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
			FileInputStream fileIn = new FileInputStream("res/pass/" + user + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			currUser = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Wrong Username", "Wrong Data", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!currUser.check(pass)) {
			JOptionPane.showMessageDialog(this, "Wrong Password", "Incorrect Validation", JOptionPane.ERROR_MESSAGE);
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

			if (currUser.getUserFolder() == null || currUser.getUserFolder() == "null") {
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
