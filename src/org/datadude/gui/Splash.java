/*******************************************************************************
 *     DataDude is a data managing application designed to have many types of data in one application
 *     Copyright (C) 2015  Ahmed R. (theTechnoKid)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.datadude.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.datadude.CoreEngine;

public class Splash extends JFrame {
	private static final long serialVersionUID = 3979584611778054086L;

	private BufferedImage splash;

	/**
	 * Create the frame.
	 */
	public Splash() {
		this.setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(500, 500);
		setLocationRelativeTo(null);

		try {
			splash = ImageIO.read(getClass().getResource("/images/dd_splash.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Apply a transparent color to the background
		// This is ALL important, without this, it won't work!
		setBackground(new Color(0, 255, 0, 0));
		getContentPane().setBackground(Color.BLACK);
		add(new JLabel(new ImageIcon(splash)));
		setVisible(true);
		try {
			Thread.sleep(2500);
			CoreEngine.init();
			dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
