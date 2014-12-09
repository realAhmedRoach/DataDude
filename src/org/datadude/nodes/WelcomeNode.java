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

package org.datadude.nodes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

public class WelcomeNode extends Node {
	private static final long serialVersionUID = 123L;

	JLabel lblWelcome;

	/**
	 * Create the panel.
	 */
	public WelcomeNode() {
		super("Welcome");
		setLayout(new FlowLayout());
		lblWelcome = new JLabel("Welcome to DataMan Beta 0.1! Click File -> New... to create a new file!");
		add(lblWelcome);
		setVisible(true);

	}

	public boolean save(String file) {
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
