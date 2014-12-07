package org.datadude;

import java.io.File;
import java.util.*;

import javax.swing.*;

import org.datadude.memory.MemoryManager;

public final class DataDude {

	public static final HashMap<String, String> fileClassNames;

	public static final String VERSION = "DataDude Alpha";

	public static final MemoryManager manager;

	private static String passLoc;
	private static String saveLoc;
	
	static {
		String pack = "org.dataman.nodes.";
		fileClassNames = new HashMap<String, String>();
		fileClassNames.put("txt", pack + "TextNode");
		manager = new MemoryManager();

	}

	private DataDude() {
	}

	public static String getPassLoc() {
		return passLoc;
	}

	protected static void setPassLoc(String passLoc) {
		DataDude.passLoc = passLoc;
	}
	protected static void setPassLoc(File passLoc) {
		DataDude.passLoc = passLoc.getAbsolutePath();
	}
	public static void setSaveLoc(File saveFile) {
		DataDude.saveLoc = saveFile.getAbsolutePath();
	}
	protected static void setSaveLoc(String saveLoc) {
		DataDude.saveLoc = saveLoc;
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
		if (fs.getSelectedFile() != null)
			return fs.getSelectedFile().getAbsolutePath();
		return "null";
	}

	public static String getFile() {
		JFileChooser fs = new JFileChooser();
		fs.showOpenDialog(null);
		if (fs.getSelectedFile() != null)
			return fs.getSelectedFile().getAbsolutePath();
		return "null";
	}



}
