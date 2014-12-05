package org.datadude;

import java.util.*;

import javax.swing.*;

import org.datadude.memory.MemoryManager;

public final class DataDude {

	public static final HashMap<String, String> fileClassNames;
	
	public static final String VERSION = "DataDude Alpha 0.1";
	
	public static final MemoryManager manager;
	
	private static String passLoc;
	
	static {
		String pack = "org.dataman.nodes.";
		fileClassNames = new HashMap<String, String>();
		fileClassNames.put("txt", pack+"TextNode");
		manager = new MemoryManager();
		
	}
	
	private DataDude() {
	}

	public static String getPassLoc() {
		return passLoc;
	}

	public static void setPassLoc(String passLoc) {
		DataDude.passLoc = passLoc;
	}

	public static void openConsole() {
		
	}
	
	public MemoryManager getMemoryManager() {
		return manager;
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
		return "null";
	}

	
}
