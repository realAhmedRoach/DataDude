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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.datadude.CoreEngine;
import org.datadude.nodes.BasicNode;
import org.datadude.nodes.CSVNode;
import org.datadude.nodes.TableNode;
import org.datadude.nodes.TextNode;

public class NewDialog extends JDialog {
	private static final long serialVersionUID = 12L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void init() {
		try {
			NewDialog dialog = new NewDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ActionListener newListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			BasicNode n = null;
			if (comboBox.getSelectedItem() == "Text") n = new TextNode(txtName.getText());
			else if  (comboBox.getSelectedItem() == "Table") n = new TableNode(txtName.getText());
			else if (comboBox.getSelectedItem()=="CSV") n = new CSVNode(txtName.getText());
			else{
				JOptionPane.showMessageDialog(null, "Not yet implemented!", "Not Available", JOptionPane.ERROR_MESSAGE);
			}
			CoreEngine.addTab(n);
			
		}

	};

	/**
	 * Create the dialog.
	 */
	public NewDialog() {
		setBounds(100, 100, 450, 300);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(3);
		borderLayout.setHgap(3);
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtName = new JTextField();
		txtName.setBounds(100, 49, 230, 42);
		txtName.setText("Name");
		contentPanel.add(txtName);
		txtName.setColumns(10);

		comboBox = new JComboBox<String>(new String[] { "Text", "Table", "Yaml", "CSV" });
		comboBox.setBounds(100, 103, 230, 56);
		contentPanel.add(comboBox);

		JLabel lblCreateNewFile = new JLabel("Create New File");
		lblCreateNewFile.setFont(new Font("Action Man", Font.PLAIN, 17));
		lblCreateNewFile.setBounds(140, 20, 137, 16);
		contentPanel.add(lblCreateNewFile);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(newListener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
