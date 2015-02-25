package org.datadude.nodes.slide;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.JPanel;

public class Slide implements Serializable {
	private static final long serialVersionUID = 7119022762742506719L;

	private String title;
	private BufferedImage image;
	private String text;
	private JPanel customContent;

	public Slide(String _title, String _text) {
		title = _title;
		text = _text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public JPanel getCustomContent() {
		return customContent;
	}

	public void setCustomContent(JPanel customContent) {
		this.customContent = customContent;
	}
}
