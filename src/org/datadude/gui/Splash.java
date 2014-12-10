package org.datadude.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Splash extends JFrame {
	private static final long serialVersionUID = 3979584611778054086L;

	private BufferedImage splash;
	
	/**
	 * Create the frame.
	 */
	public Splash() {
		setOpacity(.5f);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
		setLocationRelativeTo(null);
		
		setLocation(0, 0);
		// Set undecorated
		setUndecorated(true);
		// Apply a transparent color to the background
		// This is ALL important, without this, it won't work!
		setBackground(new Color(0, 255, 0, 0));

		// This is where we get sneaky, basically where going to 
		// supply our own content pane that does some special painting
		// for us
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		setVisible(true);
	}
}
