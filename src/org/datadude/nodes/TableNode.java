/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.datadude.nodes;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * @author theTechnoKid
 */
public class TableNode extends Node{
	private static final long serialVersionUID = -6603367461824939430L;

	JTable mainTable;
	
	public TableNode(String _title) {
		super(_title);
		
		int[] rac = this.askRowsAndColumns();
		mainTable = new JTable(rac[0], rac[1]);
		mainTable.setVisible(true);
		this.add(mainTable);
	}
	
	/**
	 * 
	 * @return An array with two values, the amount of rows and columns.
	 */
	private int[] askRowsAndColumns() {
		int[] rac = new int[2];
		try {
		rac[0] = Integer.parseInt(JOptionPane.showInputDialog("How many rows?"));
		rac[1] = Integer.parseInt(JOptionPane.showInputDialog("How many columns?"));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Your're supposed to put in a number!");
		}
		return rac;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			JFileChooser fs = new JFileChooser();
			fs.showSaveDialog(this);
		} else if (choice == exitI) System.exit(0);
	}

}
