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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public abstract class BasicNode extends JInternalFrame implements ActionListener, Node {
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

	public BasicNode(String _title) {
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
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
	public final String getFullName() {
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

	public abstract boolean save(String file);

	public abstract boolean load(String file);

	public int getTabPos() {
		return tabPos;
	}

	public void refresh() {
		pack();
		revalidate();
		repaint();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		super.setTitle(title);
		this.title = title;
	}
}
