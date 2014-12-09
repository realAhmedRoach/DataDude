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

	/**
	 * Reads lines from the current file.
	 * @param until The stop line to read
	 * @return The lines that have been read.
	 * @throws IOException If there is an error with reading.
	 */
	public String readLines(int until) throws IOException {
		/*
		 * BufferedReader br = new BufferedReader(new FileReader(currFile));
		 * char [] temp = null; if (u != -1) br.read(temp, location, u); else
		 * for (int i = location + 1; i < u; i ++) br.readLine();
		 * 
		 * br.close(); return new String(temp);
		 */
		StringBuffer temp = new StringBuffer();
		for (int i = location; i < until; i++)
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
