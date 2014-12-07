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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import java.awt.Font;

public class TextNode extends Node implements ActionListener {
	private static final long serialVersionUID = 6657L;

	private JTextArea ta;
	private JMenuBar menuBar;
	private JMenu fileM, editM;
	private JScrollPane scpane;
	private JMenuItem exitI, cutI, copyI, pasteI, selectI, saveI, loadI;
	private String pad;
	private JToolBar toolBar;
	private JLabel lblStatus;

	public TextNode(String _title) {
		title = _title;
		setTitle(title);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		
		pad = " ";
		ta = new JTextArea(); // textarea
		ta.setFont(new Font("DokChampa", Font.PLAIN, 14));
		menuBar = new JMenuBar(); // menubar
		fileM = new JMenu("File");
		editM = new JMenu("Edit");
		scpane = new JScrollPane(ta); // scrollpane and add textarea to
										// scrollpane
		exitI = new JMenuItem("Exit");
		exitI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/OfficeXP/delete.gif")));
		cutI = new JMenuItem("Cut");
		cutI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/cut.gif")));
		copyI = new JMenuItem("Copy");
		copyI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/copy.gif")));
		pasteI = new JMenuItem("Paste");
		pasteI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/paste.gif")));
		selectI = new JMenuItem("Select All"); // menuitems
		selectI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/selectall.gif")));
		saveI = new JMenuItem("Save"); // menuitems
		saveI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/save.gif")));
		loadI = new JMenuItem("Load");
		loadI.setIcon(new ImageIcon(TextNode.class.getResource("/org/fife/plaf/Office2003/open.gif")));
		toolBar = new JToolBar();

		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);

		setJMenuBar(menuBar);
		menuBar.add(fileM);
		menuBar.add(editM);

		fileM.add(saveI);
		fileM.add(loadI);
		fileM.add(exitI);

		editM.add(cutI);
		editM.add(copyI);
		editM.add(pasteI);
		editM.add(selectI);

		saveI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		copyI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
		selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));

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

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			JFileChooser fs = new JFileChooser();
			fs.showSaveDialog(this);
		} else if (choice == exitI) System.exit(0);
		else if (choice == cutI) {
			pad = ta.getSelectedText();
			ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
		} else if (choice == copyI) pad = ta.getSelectedText();
		else if (choice == pasteI) ta.insert(pad, ta.getCaretPosition());
		else if (choice == selectI) ta.selectAll();
	}

}
