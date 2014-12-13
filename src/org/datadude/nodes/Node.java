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
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class Node extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = -2795471245561416657L;
	protected String title;
	protected JMenuBar menuBar;
	protected int tabPos;
	protected String pad;
	protected JMenu fileM, editM;
	protected JMenuItem loadI, cutI, copyI, pasteI, selectI, exitI, saveI;
	protected JToolBar toolBar;
	protected Container pane;
	protected JLabel lblStatus;

	public Node(String _title) {
		title = _title;
		setTitle(title);
		// init();
	}

	/**
	 * This is how plugins get loaded. This MUST BE RIGHT or else plugins won't
	 * work
	 * 
	 * @return The full class (binary) name for the Node.
	 */
	public String getFullName() {
		return getClass().getCanonicalName();
	}

	public void init() {
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pane = getContentPane();
		pane.setLayout(new BorderLayout());

		menuBar = new JMenuBar(); // menubar
		fileM = new JMenu("File");
		editM = new JMenu("Edit");

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

		menuBar.add(fileM);
		menuBar.add(editM);

		fileM.add(saveI);
		fileM.add(loadI);
		fileM.add(exitI);

		editM.add(cutI);
		editM.add(copyI);
		editM.add(pasteI);
		editM.add(selectI);
		menuBar.add(fileM);
		menuBar.add(editM);

		fileM.add(saveI);
		fileM.add(loadI);
		fileM.add(exitI);

		editM.add(cutI);
		editM.add(copyI);
		editM.add(pasteI);
		editM.add(selectI);

		this.setEnabled(true);
		this.setVisible(true);
	}

	public abstract boolean save(String file);

	public int getTabPos() {
		return tabPos;
	}

	public void increaseTabPos() {
		tabPos++;
	}

	public void decreaseTabPos() {
		tabPos--;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		super.setTitle(title);
		this.title = title;
	}
}
