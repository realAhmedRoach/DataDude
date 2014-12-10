package org.datadude.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash extends JFrame {
	private static final long serialVersionUID = 3979584611778054086L;

	private BufferedImage splash;

	/**
	 * Create the frame.
	 */
	public Splash() {
		// setOpacity(.5f);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);

		try {
			splash = ImageIO.read(new File("images/dd_splash.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set undecorated
		setUndecorated(true);
		
		// Apply a transparent color to the background
		// This is ALL important, without this, it won't work!
		setBackground(new Color(0, 255, 0, 0));
		getContentPane().setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon(splash)));
		setVisible(true);
	}
}
