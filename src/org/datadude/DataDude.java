/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.datadude;

import java.io.File;
import java.util.*;

import javax.swing.*;

import org.datadude.memory.MemoryManager;

public final class DataDude {

	public static final HashMap<String, String> fileClassNames;

	public static final String VERSION = "DataDude kaukab-prerelease";

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
	public static void setSaveLoc(String saveLoc) {
		DataDude.saveLoc = saveLoc;
	}
	public static String getSaveLoc() {
		return saveLoc;
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
