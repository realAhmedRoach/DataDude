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

import javax.swing.JOptionPane;

public class Main {

	private static boolean firstTime;
	private static String os;

	public static void main(String[] args) {
		os = System.getProperty("os.name");
		System.out.println(os);
		/*System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("user.dir"));*/

		checkForFolders();

		if (firstTime == true) {
			ifNew();
		}

		System.out.println(DataDude.getPassLoc());

		Login.init(args);
	}

	/**
	 * Checks to see if the DataDude app data folder is there. If not, set the
	 * <code>firstTime</code> variable to true and make the folders.
	 */
	private static void checkForFolders() {
		File f = null;
		if (os.startsWith("Windows")) {
			f = new File(System.getProperty("user.home") + "\\AppData\\Local\\DataDude\\pass");
		} else {
			f = new File("/var/lib/DataDude/pass/");
		}

		if (f.isDirectory()) {
			firstTime = false;
		} else {
			firstTime = true;
			f.mkdirs();
		}

		File f2 = new File(System.getProperty("user.home") + "AppData\\Local\\DataDude\\");

		DataDude.setPassLoc(f.getAbsolutePath() + "\\");
		DataDude.setSaveLoc(f2.getAbsolutePath()+"\\");
	}

	private static void ifNew() {
		JOptionPane.showMessageDialog(null, "Welcome to DataDude!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}

}
