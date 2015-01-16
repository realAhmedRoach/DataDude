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
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import org.datadude.DataDude;
import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;
import org.datadude.datamanaging.Utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVNode extends BasicNode {
	private static final long serialVersionUID = -159829697718383315L;

	JTextField[] lines;
	JPanel textPanel;
	JButton btnNew;
	JButton btnDelete;
	int selectedIndex;

	private GridLayout layout = new GridLayout(0, 1, 10, 20);

	public CSVNode(String _title) {
		super(_title);
		init();

		textPanel = new JPanel();
		btnNew = new JButton("New");
		btnNew.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/add.png")));
		btnNew.addActionListener(new NewListener());

		btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(getClass().getResource("/images/silk/icons/delete.png")));
		btnDelete.addActionListener(new DeleteListener());

		pane.add(toolBar, BorderLayout.SOUTH);

		lblStatus = new JLabel("Status");
		toolBar.add(lblStatus);

		saveI.addActionListener(this);
		loadI.addActionListener(this);
		exitI.addActionListener(this);
		cutI.addActionListener(this);
		copyI.addActionListener(this);
		pasteI.addActionListener(this);
		selectI.addActionListener(this);

		lines = new JTextField[3];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new JTextField();
			lines[i].setColumns(70);
			lines[i].addFocusListener(new SelectListener(i));
			textPanel.add(lines[i]);
		}

		textPanel.setLayout(layout);
		textPanel.add(btnNew);
		textPanel.add(btnDelete);
		getContentPane().add(textPanel);
		setJMenuBar(menuBar);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			if (save(getTitle())) {
				lblStatus.setText("Succesfully saved CSV file.");
			} else
				lblStatus.setText("Error while saving!");
		} else if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open CSV File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded CSV file");
			else
				lblStatus.setText("Error while loading!");
		} else if (choice == pasteI)
			if (lines[selectedIndex].getSelectedText() != null)
				lines[selectedIndex].replaceSelection(DataDude.getClipboard());
			else
				try {
					lines[selectedIndex].getDocument().insertString(lines[selectedIndex].getCaretPosition(),
							DataDude.getClipboard(), null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
		else if (choice == copyI)
			DataDude.setClipboard(lines[selectedIndex].getSelectedText());
		else if (choice == cutI) {
			DataDude.setClipboard(lines[selectedIndex].getSelectedText());
			lines[selectedIndex].replaceSelection("");
		} else if(choice == selectI)
			lines[selectedIndex].selectAll();
		 else if (choice == exitI)
				exit();
	}

	@Override
	public boolean save(String file) {
		try {
			File f = new File(Login.getUser().getUserFolder() + File.separator + file + DataDudeFile.T_CSV);
			f.createNewFile();
			CSVWriter w = new CSVWriter(new FileWriter(f), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, System.getProperty("line.separator"));

			// Write It Out
			for (int i = 0; i < lines.length; i++)
				w.writeNext(lines[i].getText().split("\\s*,\\s*"), false);

			w.close();
		} catch (Exception e) {
			String text = "Exception while trying to save file:\n" + e.getMessage();
			DataDude.showError(this, e, text);
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		// Clear Panel
		textPanel.removeAll();

		try {
			CSVReader r = new CSVReader(new FileReader(file), ',');
			List<String[]> data = r.readAll();

			int newSize;

			// This works, I don't know why
			for (newSize = 0; newSize < data.size(); newSize++)
				; // Do nothing while the newSize variable keeps getting
					// incremented (violently)

			lines = new JTextField[newSize];

			for (int i = 0; i < data.size(); i++) {
				lines[i] = new JTextField();
				lines[i].setText(Utils.join(data.get(i), ","));
				lines[i].setColumns(70);
				lines[i].addFocusListener(new SelectListener(i));
				textPanel.add(lines[i]);
			}

			textPanel.add(btnNew);

			setNewTitle(new File(file));

			refresh();
			r.close();
		} catch (Exception e) {
			String text = "Exception while trying to load file:\n" + e.toString();
			e.printStackTrace();
			DataDude.showError(this, e, text);
			return false;
		}
		return true;
	}

	class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Clear Panel
			textPanel.removeAll();

			layout.setVgap(layout.getVgap() - 5);

			JTextField[] newLines = new JTextField[lines.length + 1];
			for (int i = 0; i < lines.length; i++)
				newLines[i] = lines[i]; // Sets the old text field to the new
										// one
			JTextField newField = new JTextField();
			newField.setColumns(70);
			newField.addFocusListener(new SelectListener(newLines.length - 1));
			newField.requestFocus();
			newLines[newLines.length - 1] = newField;

			lines = newLines;

			for (int i = 0; i < lines.length; i++) {
				textPanel.add(lines[i]);
			}

			textPanel.add(btnNew);
			textPanel.add(btnDelete);

			refresh();
		}
	}

	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Clear Panel
			textPanel.removeAll();

			layout.setVgap(layout.getVgap() + 5);

			JTextField[] newLines = new JTextField[lines.length - 1];
			for (int i = 0; i < (lines.length - 1); i++)
				newLines[i] = lines[i]; // Copy all lines except last one

			lines = newLines;

			for (int i = 0; i < lines.length; i++) {
				textPanel.add(lines[i]);
			}

			textPanel.add(btnNew);
			textPanel.add(btnDelete);

			refresh();
		}
	}

	class SelectListener extends FocusAdapter {
		private int index;

		public SelectListener(int i) {
			index = i;
		}

		@Override
		public void focusGained(FocusEvent e) {
			selectedIndex = index;
		}
	}
}
