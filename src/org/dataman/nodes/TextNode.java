package org.dataman.nodes;

import javax.swing.*;

public class TextNode extends Node {
	private static final long serialVersionUID = -2795471245561416657L;

	JTextArea text;
	JScrollPane a;
	
	public TextNode() {
		text = new JTextArea(40,20);
		text.setLineWrap(true);
		a = new JScrollPane(text);
		a.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		a.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(a);
	}
	
}
