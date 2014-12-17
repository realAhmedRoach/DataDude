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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;

/**
 * @author theTechnoKid
 */
public class TableNode extends BasicNode {
	private static final long serialVersionUID = -6603367461824939430L;

	JTable mainTable;

	public TableNode(String _title) {
		super(_title);
		init();
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
		setJMenuBar(menuBar);
		return rac;
	}

	
	/**
	 * Saves the file.
	 * 
	 * @return Success of save.
	 */
	public boolean save(String file) {
		File saveFile = new File(Login.getUser().getUserFolder() + File.separator + file + DataDudeFile.T_TABLE);
		try {
			FileOutputStream fileOut = new FileOutputStream(saveFile);
			ObjectOutputStream in = new ObjectOutputStream(fileOut);
			in.writeObject(mainTable);
			in.close();
			return true;
		} catch (IOException e) {
			String text = "Exception while trying to save file:\n\n" + e.getMessage();
			JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			save(getTitle());
		} else if (choice == exitI)
			System.exit(0);
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

}
