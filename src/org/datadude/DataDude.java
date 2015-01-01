/*******************************************************************************
 *     DataDude is a data managing application designed to have many types of data in one application
 *     Copyright (C) 2015  Ahmed R. (theTechnoKid)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package org.datadude;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.datadude.memory.MemoryManager;

public final class DataDude {

	public static final String VERSION = "DataDude 0.01 Prerelease";

	public static final MemoryManager manager;

	private static String passLoc;
	private static String saveLoc;

	private static CoreEngine currentEngine;

	public static final String HTML_HLP_TXT = "<html>" + "<center><h1>DataDude " + VERSION + "</h1></center>"
			+ "<h2>Intro</h2>" + "<p>DataDude is a data managing application designed to have "
			+ "many nodes, or data types, that can be opened in it.</p>" + "<h2>Nodes</h2>"
			+ "<p>Currently, DataDude has the following nodes:</p>" + "<ul>" + "<li>Text</li>"
			+ "<li>CSV (Comma-seperated Values)</li>" + "<li>Table</li>" + "</ul>"
			+ "<p>They are all self-explanatory</p>" + "<h2>License</h2>"
			+ "<p>DataDude is licensed under the GNU GPLv3 license</p>" + "<h3>Have Fun</h3>"
			+ "<p>Feel free to email me at: thetechnokid11@gmail.com</p>";

	static {
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

	static void setCurrentEngine(CoreEngine ce) {
		currentEngine = ce;
	}

	public static CoreEngine getCurrentEngine() {
		return currentEngine;
	}

	public static void openConsole() {
	}

	public MemoryManager getMemoryManager() {
		return manager;
	}

	public static void showError(Component parent, Exception e, String title) {
		String text = "Something bad happened:\n" + e;
		JOptionPane.showMessageDialog(parent, text, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void showError(Exception e, String title) {
		showError(null, e, title);
	}

	public static void showError(Exception e) {
		showError(e, "Exception");
	}

	public static String getFolder() {
		JFileChooser fs = new JFileChooser();
		fs.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fs.showSaveDialog(null);
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
