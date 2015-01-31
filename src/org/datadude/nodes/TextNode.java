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

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.datadude.*;
import org.datadude.datamanaging.*;

public class TextNode extends BasicNode {
	private static final long serialVersionUID = 6657L;

	private JTextArea ta;
	private JScrollPane scpane;

	public TextNode(String _title) {
		super(_title);
		init();
		ta = new JTextArea(); // textarea
		scpane = new JScrollPane(ta); // scrollpane and add textarea to
										// scrollpane

		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);

		pane.add(scpane, BorderLayout.CENTER);
		initStatus();
		initMenu();

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
		super.actionPerformed(e);
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open text File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded text file");
			else
				lblStatus.setText("Error while loading!");
		} else if (choice == cutI) {
			DataDude.setClipboard(ta.getSelectedText());
			ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
		} else if (choice == copyI)
			DataDude.setClipboard(ta.getSelectedText());
		else if (choice == pasteI)
			ta.insert(DataDude.getClipboard(), ta.getCaretPosition());
		else if (choice == selectI)
			ta.selectAll();
	}

	@Override
	public boolean load(String file) {
		ta.setText("");
		File loadFile = new File(file);
		try {
			BufferedReader r = new BufferedReader(new FileReader(loadFile));
			String line;
			while ((line = r.readLine()) != null)
				ta.append(line + "\n");
			setNewTitle(new File(file));
			refresh();
			r.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
