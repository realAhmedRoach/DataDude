package org.dataman.datamanaging;

import java.io.*;

public class Directory {

	/**
	 * The path of this directory
	 */
	public String path;

	public Directory() {
		this.path = System.getProperty("user.home");
	}

	public Directory(File path) {
		if (path.isDirectory())
			this.path = path.getAbsolutePath();
		else
			this.path = System.getProperty("user.home");

	}

	public Directory(String path) {
		File temp = new File(path);
		if (temp.isDirectory()) {
			if (temp.exists())
				this.path = path;
			else {
				temp.mkdirs();
				this.path = path;
			}
		} else {
			this.path = System.getProperty("user.home");
		}
	}

	public final String getPath() {
		return path;
	}

	public final void setPath(String path) {
		this.path = path;
	}

}
