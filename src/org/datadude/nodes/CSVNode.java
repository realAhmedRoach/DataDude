package org.datadude.nodes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;
import org.datadude.gui.ListDialog;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CSVNode extends BasicNode {
	private static final long serialVersionUID = -159829697718383315L;

	JTextField[] lines;
	JPanel actionPanel, textPanel;
	JButton btnNew;

	public CSVNode(String _title) {
		super(_title);
		init();

		textPanel = new JPanel();

		saveI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		copyI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

		pane.add(toolBar, BorderLayout.SOUTH);

		lblStatus = new JLabel("Status");
		toolBar.add(lblStatus);

		saveI.addActionListener(this);
		loadI.addActionListener(this);
		exitI.addActionListener(this);
		cutI.addActionListener(this);
		copyI.addActionListener(this);
		pasteI.addActionListener(this);
		selectI.addActionListener(this);

		lines = new JTextField[3];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = new JTextField();
			lines[i].setColumns(50);
			textPanel.add(lines[i]);
		}
		getContentPane().add(textPanel);
		setJMenuBar(menuBar);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == saveI) {
			if (save(getTitle())) {
				lblStatus.setText("Succesfully saved CSV file.");
			} else
				lblStatus.setText("Error while saving!");
		} else if (choice == loadI) {
			File[] f = new File(Login.getUser().getUserFolder()).listFiles();
			String[] files = new String[f.length];
			for (int i = 0; i < f.length; i++)
				files[i] = f[i].getAbsolutePath();
			ListDialog l = new ListDialog(files);
			while (l.isVisible())
				;
			if (load(l.getSelection()))
				lblStatus.setText("Succesfully loaded CSV file");
			else
				lblStatus.setText("Error while loading!");
		}

	}

	@Override
	public boolean save(String file) {
		try {
			File f = new File(Login.getUser().getUserFolder() + File.separator + file + DataDudeFile.T_CSV);
			f.createNewFile();
			CsvWriter w = new CsvWriter(f.getAbsolutePath());
			w.writeComment("Made with DataDude");
			// Header
			w.writeRecord(lines[0].getText().split(","));
			w.endRecord();

			for (int i = 1; i < lines.length; i++) {
				if (lines[i].getText().startsWith(w.getComment()+""))
						w.writeComment(lines[i].getText().substring(1));;
				w.writeRecord(lines[i].getText().split(","));
				w.endRecord();
			}
			w.flush();
			w.close();
		} catch (Exception e) {
			String text = "Exception while trying to save file:\n" + e.getMessage();
			JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		String[] headers;
		try {
			CsvReader r = new CsvReader(Login.getUser().getUserFolder() + File.separator + file);
			headers = r.getHeaders();
			r.readHeaders();
			// Print Headers {
			lines[0] = new JTextField();
			for (int i = 0; i < headers.length; i++)
				lines[0].setText(lines[0].getText() + headers[i] + ",");
			// }
			int i = 1;
			while (r.readRecord()) {
				lines[i] = new JTextField();
				lines[i].setText(lines[i].getText() + r.get(headers[i]));
				getContentPane().add(lines[i]);
				i++;
			}
		} catch (Exception e) {
			String text = "Exception while trying to load file:\n\n" + e.getMessage();
			JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
