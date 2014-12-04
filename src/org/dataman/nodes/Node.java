package org.dataman.nodes;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public abstract class Node extends JInternalFrame {
	private static final long serialVersionUID = -2795471245561416657L;
	ArrayList<Component> comps = new ArrayList<Component>();	
	protected String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		super.setTitle(title);
		this.title = title;
	}
}
