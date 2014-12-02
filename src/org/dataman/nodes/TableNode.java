/**
 * 
 */

package org.dataman.nodes;

import javax.swing.*;

/**
 * @author theTechnoKid
 */
public class TableNode extends Node {
	private static final long serialVersionUID = -6603367461824939430L;

	JTable mainTable;

	public TableNode() {
		mainTable = new JTable();
		this.setEnabled(true);
		this.setVisible(true);
	}

}
