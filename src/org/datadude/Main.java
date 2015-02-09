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

import java.io.File;

import javax.swing.*;

import com.alee.laf.WebLookAndFeel;

public class Main {

	private static boolean firstTime;
	private static String os;

	public static void main(String[] args) {
		os = (System.getProperty("os.name")).toUpperCase();

		checkForFolders();

		if (firstTime == true) {
			ifNew();
		}

		System.out.println(DataDude.getPassLoc());
		DataDude.showPreloaderAndStart();
	}

	public static void setLnF() {
		WebLookAndFeel.install();
	}

	public static boolean getFirstTime() {
		return firstTime;
	}

	/**
	 * Checks to see if the DataDude app data folder is there. If not, set the
	 * <code>firstTime</code> variable to true and make the folders.
	 */
	private static void checkForFolders() {
		File f = new File(defaultDirectory() + File.separator + "DataDude" + File.separator + "pass");

		if (f.isDirectory()) {
			firstTime = false;
		} else {
			firstTime = true;
			f.mkdirs();
		}

		DataDude.setPassLoc(f.getAbsolutePath() + File.separator);
		DataDude.setSaveLoc(f.getParent() + File.separator);
	}

	private static String defaultDirectory() {
		if (os.contains("WIN"))
			return System.getenv("APPDATA");
		else if (os.contains("MAC"))
			return System.getProperty("user.home") + "/Library/Application Support";
		else if (os.contains("NUX"))
			return System.getProperty("user.home");
		return System.getProperty("user.dir");
	}

	private static void ifNew() {
		JOptionPane.showMessageDialog(null, "Welcome to DataDude!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}

}
