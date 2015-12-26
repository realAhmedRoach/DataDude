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
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

import javax.swing.*;
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
	JButton nwClmn, nwRow, dlClmn, dlRow;

	public TableNode(String _title) {
		super(_title);
		init();
		initStatus();

		main = new JPanel(new BorderLayout(10, 10));
		pane.add(main);

		initMenu();

		mainTable = new JTable(5, 5);
		mainTable.setColumnSelectionAllowed(true);
		mainTable.setVisible(true);

		nwClmn = new JButton("New Column");
		nwClmn.setActionCommand("COLUMN");
		nwRow = new JButton("New Row");
		nwRow.setActionCommand("ROW");
		nwClmn.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/add.png")));
		nwRow.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/add.png")));
		nwClmn.addActionListener(newListener);
		nwRow.addActionListener(newListener);

		dlClmn = new JButton("Delete Column");
		dlClmn.setActionCommand("COLUMN");
		dlRow = new JButton("Delete Row");
		dlRow.setActionCommand("ROW");
		dlClmn.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/delete.png")));
		dlRow.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/delete.png")));
		dlClmn.addActionListener(delListener);
		dlRow.addActionListener(delListener);

		setJMenuBar(menuBar);
		main.add(new JScrollPane(mainTable));
		main.add(nwClmn, BorderLayout.LINE_START);
		main.add(nwRow, BorderLayout.LINE_END);
		main.add(dlClmn, BorderLayout.PAGE_START);
		main.add(dlRow, BorderLayout.PAGE_END);
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

			Vector c = new Vector(100);
			c = (Vector) o.readObject();
			DefaultTableModel m = new DefaultTableModel();
			m.setDataVector(c, (Vector) o.readObject());

			JTable newTable = new JTable(m);
			newTable.setDragEnabled(true);
			newTable.setColumnSelectionAllowed(true);
			mainTable = newTable;
			main.add(new JScrollPane(mainTable));
			main.add(nwClmn, BorderLayout.LINE_START);
			main.add(nwRow, BorderLayout.LINE_END);
			main.add(dlClmn, BorderLayout.PAGE_START);
			main.add(dlRow, BorderLayout.PAGE_END);

			setNewTitle(new File(file));
			
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
		super.actionPerformed(e);
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open Table File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded Table file");
			else
				lblStatus.setText("Error while loading!");
		} else if (choice == pasteI)
			mainTable.setValueAt(DataDude.getClipboard(), mainTable.getSelectedRow(), mainTable.getSelectedColumn());
		else if (choice == copyI)
			DataDude.setClipboard((String) mainTable.getValueAt(mainTable.getSelectedRow(),
					mainTable.getSelectedColumn()));
		else if (choice == cutI) {
			mainTable.setValueAt("", mainTable.getSelectedRow(), mainTable.getSelectedColumn());
			DataDude.setClipboard((String) mainTable.getValueAt(mainTable.getSelectedRow(),
					mainTable.getSelectedColumn()));
		}
	}

	private Vector<String> getColumnNames() {
		Vector<String> names = new Vector<String>();
		for (int i = 0; i < mainTable.getColumnCount(); i++)
			names.add(mainTable.getColumnName(i));
		return names;
	}

	private ActionListener newListener = (ActionEvent e) -> {
		DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
		if ((e.getActionCommand()).equalsIgnoreCase("COLUMN")) {
			model.addColumn(null);
		} else {
			model.addRow((Vector<?>) null);
		}
	};
	private ActionListener delListener = (ActionEvent e) -> {
		DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
		if ((e.getActionCommand()).equalsIgnoreCase("COLUMN")) {
			int col = mainTable.getSelectedColumn() <= -1 ? 0 : mainTable.getSelectedColumn();
			removeCol(col);
		} else {
			int row = mainTable.getSelectedRow() <= -1 ? 0 : mainTable.getSelectedRow();
			model.removeRow(row);
		}
	};

	private void removeCol(int id) {
		DefaultTableModel tmp = new DefaultTableModel();
		int columns = mainTable.getColumnCount();
		for (int i = 0; i < columns; i++) {
			if (i != id)
				tmp.addColumn(mainTable.getColumnName(i));
		}
		int rows = mainTable.getRowCount();
		String datos[] = new String[columns - 1];
		for (int row = 0; row < rows; row++) {
			for (int col = 0, sel = 0; col < columns; col++, sel++) {
				if (col != id)
					datos[sel] = (String) mainTable.getValueAt(row, col);
				else
					sel--;
			}
			tmp.addRow(datos);
		}
		mainTable.setModel(tmp);
	}
}
