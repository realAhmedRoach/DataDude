package org.dataman;

import javax.swing.*;

public final class DataMan {

	private DataMan() {
	}

	
	public static String getFolder() {
		JFileChooser fs = new JFileChooser();
		fs.setDialogType(JFileChooser.DIRECTORIES_ONLY);
		fs.showOpenDialog(null);
		if(fs.getSelectedFile() != null)
			return fs.getSelectedFile().getAbsolutePath();
		return "null";
	}

	public static String getFile() {
		JFileChooser fs = new JFileChooser();
		fs.showOpenDialog(null);
		if(fs.getSelectedFile() != null)
			return fs.getSelectedFile().getAbsolutePath();
			fs = null;
		return "null";
	}

	
}
