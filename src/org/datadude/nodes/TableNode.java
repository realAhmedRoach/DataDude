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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.datadude.DataDude;
import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;

/**
 * Node for showing tables
 * 
 * @author theTechnoKid
 */
public class TableNode extends BasicNode {
	private static final long serialVersionUID = -6603367461824939430L;

	JPanel main;
	JTable mainTable;

	public TableNode(String _title) {
		super(_title);
		init();
		lblStatus = new JLabel("Status");
		toolBar.add(lblStatus);

		main = new JPanel();

		pane.add(toolBar, BorderLayout.SOUTH);
		pane.add(main);

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
		// TODO: Add new row and column buttons

		setJMenuBar(menuBar);
		main.add(mainTable);
	}

	private int[] askRowsAndColumns() {
		int[] rac = new int[2];
		try {
			rac[0] = Integer.parseInt(JOptionPane.showInputDialog("How many rows?"));
			if (rac[0] > 200)
				rac[0] = 200;
			rac[1] = Integer.parseInt(JOptionPane.showInputDialog("How many columns?"));
			if (rac[1] > 200)
				rac[1] = 200;
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
			ObjectOutputStream in = new ObjectOutputStream(new FileOutputStream(saveFile));
			DefaultTableModel m = (DefaultTableModel) mainTable.getModel();
			@SuppressWarnings("rawtypes")
			Vector c = m.getDataVector();
			in.writeObject(c);
			in.writeObject(getColumnNames());
			in.close();
			return true;
		} catch (NotSerializableException nse) {
			// catching exceptions is for communists
			return true;
		} catch (IOException e) { // they made me do this, beyond my will
			DataDude.showError(this, e, "Save failed");
			return false;
		}

	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean load(String file) {
		File loadFile = new File(file);
		main.removeAll();
		try {
			ObjectInputStream o = new ObjectInputStream(new FileInputStream(loadFile));

			Vector c = (Vector) o.readObject();
			DefaultTableModel m = new DefaultTableModel();
			m.setDataVector(c, (Vector) o.readObject());

			JTable newTable = new JTable(m);
			newTable.setDragEnabled(true);
			main.add(newTable);

			o.close();
			refresh();
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
			if (save(getTitle()))
				lblStatus.setText("Successfully saved Table!");
			else
				lblStatus.setText("Error while saving!");
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

	private Vector<String> getColumnNames() {
		Vector<String> names = new Vector<String>();
		for (int i = 0; i < mainTable.getColumnCount(); i++) 
			names.add(mainTable.getColumnName(i));
		return names;
	}
}
