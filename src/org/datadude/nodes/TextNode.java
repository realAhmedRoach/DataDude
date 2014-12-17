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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;
import org.datadude.gui.ListDialog;

public class TextNode extends BasicNode implements ActionListener {
	private static final long serialVersionUID = 6657L;

	private JTextArea ta;
	private JScrollPane scpane;

	public TextNode(String _title) {
		super(_title);
		init();
		pad = " ";
		ta = new JTextArea(); // textarea
		ta.setFont(new Font("DokChampa", Font.PLAIN, 14));
		scpane = new JScrollPane(ta); // scrollpane and add textarea to
										// scrollpane

		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);

		saveI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		copyI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		pane.add(scpane, BorderLayout.CENTER);
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

		setJMenuBar(menuBar);
		setVisible(true);
	}

	/**
	 * Saves the file.
	 * 
	 * @return Success of save.
	 */
	public boolean save(String file) {
		File saveFile = new File(Login.getUser().getUserFolder() + File.separator + file + DataDudeFile.T_TEXT);
		try {
			FileWriter out = new FileWriter(saveFile);
			BufferedWriter buffer = new BufferedWriter(out);
			buffer.write(ta.getText());
			buffer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			if (save(getTitle())) {
				lblStatus.setText("Succesfully saved text file.");
			}
		} else if (choice == loadI) {
			File[] f = new File(Login.getUser().getUserFolder()).listFiles();
			String[] files = new String[f.length];
			for (int i = 0; i < f.length; i++)
				files[i] = f[i].getAbsolutePath();
			ListDialog l = new ListDialog(files);
			while (l.isVisible())
				;
			if (load(l.getSelection()))
				lblStatus.setText("Succesfully loaded CSV file");
			else
				lblStatus.setText("Error while loading!");
		} else if (choice == cutI) {
			pad = ta.getSelectedText();
			ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
		} else if (choice == copyI)
			pad = ta.getSelectedText();
		else if (choice == pasteI)
			ta.insert(pad, ta.getCaretPosition());
		else if (choice == selectI)
			ta.selectAll();
	}

	@Override
	public boolean load(String file) {
		File loadFile = new File(Login.getUser().getUserFolder() + File.separator + file);
		try {
			BufferedReader r = new BufferedReader(new FileReader(loadFile));
			String line;
			while ((line = r.readLine()) != null)
				ta.setText(line + "\n");
			r.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
