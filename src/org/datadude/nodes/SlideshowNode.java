package org.datadude.nodes;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;

import org.datadude.nodes.slide.Slide;

public class SlideshowNode extends BasicNode {
	private static final long serialVersionUID = -9190594824769599302L;

	ArrayList<Slide> slides;
	JPanel buttons;
	JButton next,prev;
	
	public SlideshowNode(String _title) {
		super(_title);
		slides = new ArrayList<>(3);
		createSampleSlides();
		
		buttons = new JPanel();
		next = new JButton("Next >");
		prev = new JButton("< Previous");
		add(showSlides());
	}

	private JPanel showSlides() {
		JLabel title = new JLabel(slides.get(0).getTitle());
		JLabel text = new JLabel(slides.get(0).getText());
		JPanel p = new JPanel();
		p.add(text);
		p.add(title);
		return p;
	}
	

	private void createSampleSlides() {
		for (int i = 0; i < 3; i++) {
			Slide s  = new Slide("HI","This is hi #"+i);
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
}
