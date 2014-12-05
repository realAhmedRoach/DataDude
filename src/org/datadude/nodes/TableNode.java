package org.datadude.nodes;

import javax.swing.*;

/**
 * @author theTechnoKid
 */
public class TableNode extends Node {
	private static final long serialVersionUID = -6603367461824939430L;

	JTable mainTable;

	public TableNode() {
		int[] rac = this.askRowsAndColumns();
		mainTable = new JTable(rac[0], rac[1]);
		mainTable.setVisible(true);
		this.add(mainTable);
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	
	/**
	 * 
	 * @return An array with two values, the amount of rows and columns.
	 */
	private int[] askRowsAndColumns() {
		int[] rac = new int[2];
		try {
		rac[0] = Integer.parseInt(JOptionPane.showInputDialog("How many rows?"));
		rac[1] = Integer.parseInt(JOptionPane.showInputDialog("How many columns?"));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Your're supposed to put in a number!");
		}
		return rac;
	}

}
