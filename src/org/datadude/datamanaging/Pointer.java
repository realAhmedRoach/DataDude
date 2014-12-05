package org.datadude.datamanaging;

import java.io.*;

import org.apache.commons.io.FileUtils;

public class Pointer {

	int location;
	int type;
	File currFile;

	public static final int FILE_POINTER = 0;
	public static final int DIRECTORY_POINTER = 1;

	public Pointer(File file, int loc, int type) {
		this.currFile = file;
		this.location = loc;
		this.type = type;
	}

	public Pointer(int loc, int type) {
		this.location = loc;
		this.type = type;
	}

	public Pointer(int type) {
		this(0, type);
	}

	public String readLines(int u) throws IOException, IllegalAccessException {
		/*
		 * BufferedReader br = new BufferedReader(new FileReader(currFile));
		 * char [] temp = null; if (u != -1) br.read(temp, location, u); else
		 * for (int i = location + 1; i < u; i ++) br.readLine();
		 * 
		 * br.close(); return new String(temp);
		 */
		StringBuffer temp = new StringBuffer();
		for (int i = location; i < u; i++)
			temp.append(FileUtils.readLines(currFile).get(i));
		return new String(temp);
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public File getCurrFile() {
		return currFile;
	}

	public void setCurrFile(File currFile) {
		this.location = 0;
		this.currFile = currFile;
	}

}
