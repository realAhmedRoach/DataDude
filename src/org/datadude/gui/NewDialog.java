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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.datadude.DataDude;
import org.datadude.nodes.*;

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
			dialog.setLocationRelativeTo(DataDude.getCurrentEngine());
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ActionListener newListener = (ActionEvent e) -> {
		dispose();
		BasicNode n = null;
		if (comboBox.getSelectedItem() == "Text")
			n = new TextNode(txtName.getText());
		else if (comboBox.getSelectedItem() == "Table")
			n = new TableNode(txtName.getText());
		else if (comboBox.getSelectedItem() == "CSV")
			n = new CSVNode(txtName.getText());
		else if (comboBox.getSelectedItem() == "Slideshow")
			n = new SlideshowNode(txtName.getText());
		else {
			JOptionPane.showMessageDialog(null, "Not yet implemented!", "Not Available", JOptionPane.ERROR_MESSAGE);
		}
		DataDude.getCurrentEngine().addTab(n);
	};

	/**
	 * Create the dialog.
	 */
	public NewDialog() {
		setTitle("New File");
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setSize(440, 290);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(3);
		borderLayout.setHgap(3);
		contentPanel.setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		JPanel it = new JPanel();
		txtName = new JTextField();
		txtName.setText("Name");
		txtName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (txtName.getText().isEmpty()) {
					txtName.setText("Name");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (txtName.getText().equals("Name")) {
					txtName.setText("");
				}
			}
		});
		contentPanel.setLayout(new BorderLayout(0, 0));
		SpringLayout sl_it = new SpringLayout();
		sl_it.putConstraint(SpringLayout.NORTH, txtName, 40, SpringLayout.NORTH, it);
		sl_it.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, it);
		sl_it.putConstraint(SpringLayout.SOUTH, txtName, -107, SpringLayout.SOUTH, it);
		sl_it.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, it);
		it.setLayout(sl_it);
		it.add(txtName);
		txtName.setColumns(10);

		String[] values = new String[] { "Text", "Table", "CSV", "Slideshow" };

		JLabel lblCreateNewFile = new JLabel("Create New File", SwingConstants.CENTER);
		lblCreateNewFile.setFont(new Font("Action Man", Font.PLAIN, 17));
		contentPanel.add(lblCreateNewFile, BorderLayout.NORTH);
		comboBox = new JComboBox<String>(values);
		sl_it.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, txtName);
		sl_it.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, txtName);
		sl_it.putConstraint(SpringLayout.SOUTH, comboBox, -44, SpringLayout.SOUTH, it);
		sl_it.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, txtName);
		it.add(comboBox);

		contentPanel.add(it);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(newListener);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);

	}
}
