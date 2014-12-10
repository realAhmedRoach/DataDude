package org.datadude.gui;

import java.awt.image.*;

import javax.swing.JFrame;

public class Splash extends JFrame {
	private static final long serialVersionUID = 3979584611778054086L;

	private BufferedImage splash;
	
	/**
	 * Create the frame.
	 */
	public Splash() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250,500);
		setLocationRelativeTo(null);
		setUndecorated(true);
	}

}
