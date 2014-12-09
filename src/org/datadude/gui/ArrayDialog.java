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

	private String[] getValues() {
		return values;
	}

	private void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * Create the dialog.
	 */
	public ArrayDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 200, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 2, 0, 30));
		{
			JLabel lblSel1 = new JLabel("Sel1");
			contentPanel.add(lblSel1);
		}
		{
			txtArr1 = new JTextField();
			txtArr1.setText("arr1");
			contentPanel.add(txtArr1);
			txtArr1.setColumns(10);
		}
		{
			JLabel lblSel2 = new JLabel("Sel2");
			contentPanel.add(lblSel2);
		}
		{
			txtArr2 = new JTextField();
			txtArr2.setText("arr2");
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
						String [] text = {txtArr1.getText(), txtArr2.getText()};
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
