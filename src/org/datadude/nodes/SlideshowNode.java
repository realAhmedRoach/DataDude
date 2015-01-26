package org.datadude.nodes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.datadude.nodes.slide.Slide;

public class SlideshowNode extends BasicNode {
	private static final long serialVersionUID = -9190594824769599302L;

	Font TITLE = new Font("Action Man", Font.BOLD, 72);

	ArrayList<Slide> slides;
	JPanel buttons, slidePanel,editPanel;
	JLabel lblTitle,lblText,lblImage;
	JTextField txtTitle,txtText;
	JButton next, prev;
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
		editPanel.setLayout(new BoxLayout(editPanel,BoxLayout.Y_AXIS));
		lblTitle = new JLabel("Title:");
		lblText = new JLabel("Text:");
		lblImage = new JLabel("Image:");
		txtTitle = new JTextField();
		txtText = new JTextField();
		editPanel.add(lblTitle);
		editPanel.add(txtTitle);
		editPanel.add(lblText);
		editPanel.add(txtText);
		editPanel.add(lblImage);
		
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
		pane.add(editPanel,BorderLayout.LINE_END);
		pane.add(buttons, BorderLayout.SOUTH);
	}

	private JPanel showSlides() {
		JLabel title = new JLabel(slides.get(slideNo).getTitle());
		title.setFont(TITLE);
		JLabel text = new JLabel(slides.get(slideNo).getText());
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.add(title);
		p.add(text);
		return p;
	}

	private void createSampleSlides() {
		for (int i = 1; i < 4; i++) {
			Slide s = new Slide("HI", "This is hi #" + i);
			slides.add(s);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public boolean save(String file) {
		return false;
	}

	@Override
	public boolean load(String file) {
		return false;
	}
	private ActionListener prevnext = (ActionEvent e) -> {
		pane.remove(slidePanel);
		if (e.getActionCommand() == "NEXT") {
			slideNo++;
			if(slideNo==slides.size())
				slideNo=0;
		} else if (e.getActionCommand() == "PREV") {
			slideNo--;
			if(slideNo<0)
				slideNo=slides.size()-1;
		}
		slidePanel = showSlides();
		pane.add(slidePanel);
		refresh();
	};
}
