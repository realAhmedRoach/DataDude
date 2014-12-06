package org.datadude.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.datadude.CoreEngine;
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
			System.out.println(comboBox.getSelectedItem());
			TextNode n = new TextNode(txtName.getText());
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

		comboBox = new JComboBox<String>(new String[] { "Text", "Table"});
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
