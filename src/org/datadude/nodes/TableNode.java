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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.datadude.DataDude;
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
		lblStatus = new JLabel();
		toolBar.add(lblStatus);
		
		saveI.addActionListener(this);
		loadI.addActionListener(this);
		exitI.addActionListener(this);
		cutI.addActionListener(this);
		copyI.addActionListener(this);
		pasteI.addActionListener(this);
		selectI.addActionListener(this);
		
		int[] rac = this.askRowsAndColumns();
		mainTable = new JTable(rac[0], rac[1]);
		mainTable.setVisible(true);
		// TODO: Add new row and column button

		setJMenuBar(menuBar);
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
			DataDude.showError(this, e, "Save failed");
			return false;
		}
	}

	@Override
	public boolean load(String file) {
		File loadFile = new File(file);
		removeAll();
		try {
			FileInputStream f = new FileInputStream(loadFile);
			ObjectInputStream o = new ObjectInputStream(f);
			JTable newTable = (JTable) o.readObject();
			add(newTable);
			o.close();
			revalidate();
			repaint();
			return true;
		} catch (Exception e) {
			DataDude.showError(this, e, "Load failed");
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			save(getTitle());
		} else if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open Table File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded Table file");
			else
				lblStatus.setText("Error while loading!");
		}
	}

}
