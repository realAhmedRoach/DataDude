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
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import org.datadude.CoreEngine;
import org.datadude.DataDude;

public abstract class BasicNode extends JInternalFrame implements ActionListener, Node {
	private static final long serialVersionUID = -2795471245561416657L;
	protected String title;
	protected JMenuBar menuBar;
	protected JMenu fileM, editM;
	protected JMenuItem loadI, cutI, copyI, pasteI, selectI, exitI, saveI;
	protected JToolBar toolBar;
	protected Container pane;
	protected JLabel lblStatus;

	public BasicNode(String _title) {
		setBorder(new BevelBorder(BevelBorder.RAISED, Color.CYAN, Color.RED, Color.GREEN, Color.YELLOW));
		title = _title;
		setTitle(title);
	}

	/**
	 * This is how plugins get loaded. This MUST BE RIGHT or else plugins won't
	 * work
	 * 
	 * @return The full class (binary) name for the Node.
	 */
	public final String getFullName() {
		return getClass().getCanonicalName();
	}

	@Override
	public void init() {
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pane = getContentPane();
		pane.setLayout(new BorderLayout());

		menuBar = new JMenuBar(); // menubar
		fileM = new JMenu("File");
		editM = new JMenu("Edit");

		exitI = new JMenuItem("Exit");
		exitI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/alee/laf/filechooser/icons/remove.png")));
		cutI = new JMenuItem("Cut");
		cutI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/sun/javafx/scene/web/skin/Cut_16x16_JFX.png")));
		copyI = new JMenuItem("Copy");
		copyI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/alee/extended/ninepatch/icons/copy.png")));
		pasteI = new JMenuItem("Paste");
		pasteI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/alee/extended/ninepatch/icons/paste.png")));
		selectI = new JMenuItem("Select All");
		saveI = new JMenuItem("Save"); // menuitems
		saveI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/alee/extended/ninepatch/icons/save.png")));
		loadI = new JMenuItem("Load");
		loadI.setIcon(new ImageIcon(BasicNode.class.getResource("/com/alee/extended/ninepatch/icons/open.png")));
		toolBar = new JToolBar();

		saveI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		exitI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		copyI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

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

	@Override
	public abstract boolean save(String file);

	@Override
	public abstract boolean load(String file);

	public void refresh() {
		pack();
		revalidate();
		repaint();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		this.title = title;
	}

	public void cleanup() {
	}

	protected void exit() {
		DataDude.getCurrentEngine().removeTab(this);
	}

	protected void initStatus() {
		lblStatus = new JLabel("Status");
		toolBar.add(lblStatus);
		pane.add(toolBar, BorderLayout.SOUTH);
	}
	
	protected void initMenu() {
		saveI.addActionListener(this);
		loadI.addActionListener(this);
		exitI.addActionListener(this);
		cutI.addActionListener(this);
		copyI.addActionListener(this);
		pasteI.addActionListener(this);
		selectI.addActionListener(this);
	}
	
	protected void setNewTitle(File f) {
		CoreEngine c = DataDude.getCurrentEngine();
		if ((f.getName()).contains("."))
			c.setTitleAt(c.getNodes().indexOf(this), (f.getName()).split(".")[0]);
		else
			c.setTitleAt(c.getNodes().indexOf(this), f.getName());
	}
}
