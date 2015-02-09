package org.datadude.nodes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.datadude.DataDude;
import org.datadude.Login;
import org.datadude.datamanaging.DataDudeFile;
import org.datadude.nodes.slide.Slide;

public class SlideshowNode extends BasicNode {
	private static final long serialVersionUID = -9190594824769599302L;

	Font TITLE = new Font("Action Man", Font.BOLD, 72);

	ArrayList<Slide> slides;
	JPanel buttons, slidePanel, editPanel;
	JLabel lblTitle, lblText, lblImage;
	JTextField txtTitle, txtText;
	JButton next, prev, save,browse;
	File imgFile;
	int slideNo;

	public SlideshowNode(String _title) {
		super(_title);
		init();
		initMenu();
		initStatus();
		slides = new ArrayList<>(3);
		createSampleSlides();
		slidePanel = showSlides();

		editPanel = new JPanel();
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
		lblTitle = new JLabel("Title:");
		lblText = new JLabel("Text:");
		lblImage = new JLabel("Image:");
		txtTitle = new JTextField();
		txtText = new JTextField();
		save = new JButton("Save");
		save.addActionListener((ActionEvent e) -> {
			setSlides();
		});
		browse = new JButton("Browse");
		browse.addActionListener(imgBrowse);
		editPanel.add(lblTitle);
		editPanel.add(txtTitle);
		editPanel.add(lblText);
		editPanel.add(txtText);
		editPanel.add(lblImage);
		editPanel.add(browse);
		editPanel.add(save);

		buttons = new JPanel();
		next = new JButton("Next >");
		prev = new JButton("< Previous");
		next.setActionCommand("NEXT");
		prev.setActionCommand("PREV");
		next.addActionListener(prevnext);
		prev.addActionListener(prevnext);
		buttons.add(prev);
		buttons.add(next);
		pane.add(slidePanel);
		pane.add(editPanel, BorderLayout.LINE_END);
		pane.add(buttons, BorderLayout.SOUTH);

		editM.setVisible(false);
		setJMenuBar(menuBar);
	}

	private JPanel showSlides() {
		Slide curr = slides.get(slideNo);
		JLabel title = new JLabel(curr.getTitle(), SwingConstants.CENTER);
		title.setFont(TITLE);
		JLabel text = new JLabel(curr.getText(), SwingConstants.LEADING);
		JLabel img = null;
		if(curr.getImage()!=null)
			img =new JLabel(new ImageIcon(curr.getImage()),SwingConstants.CENTER);;
		JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
		p.setMaximumSize(p.getPreferredSize());
		p.add(title);
		p.add(text);
		if(img!=null)
			p.add(img);
		return p;
	}

	private void setSlides() {
		Slide curr = slides.get(slideNo);
		curr.setTitle(txtTitle.getText());
		curr.setText(txtText.getText());
		try {
			if (imgFile!=null)
				curr.setImage(ImageIO.read(imgFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pane.remove(slidePanel);
		slidePanel = showSlides();
		pane.add(slidePanel);
		refresh();
	}

	private void createSampleSlides() {
		for (int i = 1; i < 4; i++) {
			Slide s = new Slide("HI", "This is hi #" + i);
			slides.add(s);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		JMenuItem choice = (JMenuItem) e.getSource();
		if (choice == loadI) {
			JFileChooser l = new JFileChooser();
			l.setCurrentDirectory(new File(Login.getUser().getUserFolder()));
			l.setDialogTitle("Open slideshow File");
			l.showOpenDialog(this);
			if (load(l.getSelectedFile().getAbsolutePath()))
				lblStatus.setText("Succesfully loaded slideshow");
			else
				lblStatus.setText("Error while loading!");
		}
	}

	@Override
	public boolean save(String file) {
		File saveFile = new File(Login.getUser().getUserFolder() + File.separator + file + DataDudeFile.T_SLIDESHOW);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
			oos.writeObject(slides);
			oos.close();
			return true;
		} catch (IOException e) {
			DataDude.showError(this, e, "Error while saving!");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean load(String file) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			slides = (ArrayList<Slide>) in.readObject();
			in.close();
			pane.remove(slidePanel);
			slidePanel = showSlides();
			pane.add(slidePanel);
			refresh();
			return true;
		} catch (IOException e) {
			DataDude.showError(this, e, "Error while loading!");
			return false;
		} catch (ClassNotFoundException e) {
			DataDude.showError(this, e, "Error while loading!");
			return false;
		}
	}

	private ActionListener prevnext = (ActionEvent e) -> {
		pane.remove(slidePanel);
		if (e.getActionCommand() == "NEXT") {
			slideNo++;
			if (slideNo == slides.size())
				slideNo = 0;
		} else if (e.getActionCommand() == "PREV") {
			slideNo--;
			if (slideNo < 0)
				slideNo = slides.size() - 1;
		}
		txtTitle.setText(slides.get(slideNo).getTitle());
		txtText.setText(slides.get(slideNo).getText());
		imgFile = null;
		slidePanel = showSlides();
		pane.add(slidePanel);
		refresh();
	};
	private ActionListener imgBrowse = (ActionEvent e) -> {
		JFileChooser fileOpen = new JFileChooser();

		// Get array of available formats
		String[] suffices = ImageIO.getReaderFileSuffixes();
		for (int i = 0; i < suffices.length; i++) {
			FileFilter filter = new FileNameExtensionFilter(suffices[i] + " files", suffices[i]);
			fileOpen.addChoosableFileFilter(filter);
		}

		int ret = fileOpen.showDialog(this, "Open Image");
		if(ret!=JFileChooser.CANCEL_OPTION) {
			imgFile = fileOpen.getSelectedFile();
		}
	};
}