package org.dataman.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import org.dataman.Login;
import org.dataman.security.User;




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
				File f = new File("res/pass/"+user.getText()+".ser");
				f.mkdirs();
				f.createNewFile();
				currUser = new User(user.getText(), pass.getPassword());
				currUser.encrypt();
				currUser.save();
				@SuppressWarnings("unused")
				Login l = new Login();
				dispose();
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		
	};
	private JTextField user;
	private JPasswordField pass;

	/**
	 * Create the dialog.
	 */
	public void initWindow() {
		setTitle("Register to DataMan");
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUsername = new JLabel("Username");
			lblUsername.setBounds(5, 32, 209, 44);
			contentPanel.add(lblUsername);
		}
		{
			user = new JTextField();
			user.setBounds(219, 32, 209, 36);
			contentPanel.add(user);
			user.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(5, 88, 209, 44);
			contentPanel.add(lblPassword);
		}
		
		pass = new JPasswordField();
		pass.setBounds(219, 88, 209, 36);
		contentPanel.add(pass);
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
