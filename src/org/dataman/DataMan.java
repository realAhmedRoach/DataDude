package org.dataman;

import javax.swing.*;

import org.dataman.memory.MemoryManager;

public final class DataMan {

	private DataMan() {
	}

	
	public static void openConsole() {
		
	}
	
	public MemoryManager getMemoryManager() {
		return new MemoryManager();
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
