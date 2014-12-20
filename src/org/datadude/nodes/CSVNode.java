package org.datadude.nodes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

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
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open CSV File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
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
			CSVWriter w = new CSVWriter(new FileWriter(f), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, System.getProperty("line.separator"));

			// Headers
			for (int i = 0; i < lines.length; i++)
				w.writeNext(lines[i].getText().split(","), false);

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
		// Clear Panel
		textPanel.removeAll();

		try {
			CSVReader r = new CSVReader(new FileReader(file), ',');
			List<String[]> data = r.readAll();
			
			for(int i = 0; i<data.size(); i++){
				lines[i] = new JTextField();
				lines[i].setText(String.join(",", data.get(i)));
				lines[i].setColumns(50);
				textPanel.add(lines[i]);
			} 

			revalidate();
			repaint();
			r.close();
		} catch (Exception e) {
			String text = "Exception while trying to load file:\n\n" + e.toString();
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
