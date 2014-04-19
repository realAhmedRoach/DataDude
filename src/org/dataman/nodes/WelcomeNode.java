package org.dataman.nodes;


import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;

public class WelcomeNode extends Node {
	private static final long serialVersionUID = 123L;

	JLabel lblWelcome; 
	/**
	 * Create the panel.
	 */
	public WelcomeNode() {
		setLayout(new FlowLayout());
		lblWelcome = new JLabel("Welcome to DataMan Beta 0.1! Click File -> New... to create a new file!");
		add(lblWelcome);
		setVisible(true);

	}

}
