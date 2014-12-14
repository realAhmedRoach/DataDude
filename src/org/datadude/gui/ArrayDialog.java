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
package org.datadude.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrayDialog extends JDialog {

	private static final long serialVersionUID = 7741891346675409491L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtArr1;
	private JTextField txtArr2;
	private String[] values;
	
	/**
	 * Launch the application.
	 */

	public String[] getValues() {
		return values;
	}

	private void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * Create the dialog.
	 */
	public ArrayDialog(String sel1, String sel2) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 200, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 2, 0, 30));
		{
			JLabel lblSel1 = new JLabel(sel1);
			contentPanel.add(lblSel1);
		}
		{
			txtArr1 = new JTextField();
			contentPanel.add(txtArr1);
			txtArr1.setColumns(10);
		}
		{
			JLabel lblSel2 = new JLabel(sel2);
			contentPanel.add(lblSel2);
		}
		{
			txtArr2 = new JTextField();
			contentPanel.add(txtArr2);
			txtArr2.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String[] text = {txtArr1.getText(), txtArr2.getText()};
						setValues(text);
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
