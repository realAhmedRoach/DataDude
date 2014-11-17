package org.dataman;

import java.io.File;

import javax.swing.JOptionPane;

public class Main {

	private static boolean firstTime;
	private static String os;

	public static void main(String[] args) {
		os = System.getProperty("os.name");
		System.out.println(os);
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("java.home"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("user.dir"));

		checkForFolders();

		if (firstTime == true) {
			ifNew();
		}
		
		Login.init(args);
	}

	private static void checkForFolders() {
		File f = null;
		if (os.startsWith("Windows")) {
			f = new File(System.getProperty("user.home") + File.separator
					+ "AppData" + File.separator + "DataDude" + File.separator
					+ "pass");
		} else {
			f = new File("/var/lib/DataDude/pass");
		}

		if (f.isDirectory()) {
			firstTime = false;
		} else {
			firstTime = true;
			f.mkdirs();
		}
		
		DataDude.setPassLoc(f.getAbsolutePath());
	}

	private static void ifNew() {
		if (firstTime == true) {
			JOptionPane.showMessageDialog(null, "Welcome to DataDude!",
					"Welcome", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
