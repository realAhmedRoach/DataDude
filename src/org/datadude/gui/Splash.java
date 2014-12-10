package org.datadude.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.datadude.CoreEngine;

public class Splash extends JFrame {
	private static final long serialVersionUID = 3979584611778054086L;

	private BufferedImage splash;

	/**
	 * Create the frame.
	 */
	public Splash() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(500, 500);
		setLocationRelativeTo(null);

		try {
			splash = ImageIO.read(getClass().getResource("/images/dd_splash.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Set undecorated
		this.setUndecorated(true);
		
		// Apply a transparent color to the background
		// This is ALL important, without this, it won't work!
		setBackground(new Color(0, 255, 0, 0));
		getContentPane().setBackground(Color.BLACK);
		//setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon(splash)));
		setVisible(true);
		try {
			Thread.sleep(3000);
			CoreEngine.init();
			dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
