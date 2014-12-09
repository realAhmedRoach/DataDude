/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    DataDude is free software: you can redistribute it and/or modify
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

package org.datadude.datamanaging;

import java.io.*;

public class DataDudeFile implements Serializable {

	private static final long serialVersionUID = 730623L;

	public static final String T_TEXT = ".txt";
	public static final String T_TABLE = ".ddt";
	public static final String T_DIAGRAM = ".ddd";
	public static final String T_PROPERTIES = ".properties";
	public static final String T_OTHER = ".ddf";

	protected String type;
	protected String dir;
	protected String name;
	protected Pointer filePointer;

	public DataDudeFile(String ext, String name, String dir) {
		type = ext;
		this.name = name;
		this.dir = dir;
		filePointer = new Pointer(Pointer.FILE_POINTER);
	}

	public final String getType() {
		return type;
	}

	public final void setType(String type) {
		this.type = type;
	}

	public final String getName() {
		return name;
	}

	public final boolean setName(String name) throws IOException {

		File file = new File(this.name);
		this.name = name;
		// File (or directory) with new name
		File file2 = new File(this.name);
		if (file2.exists())
			throw new java.io.IOException("file exists");

		// Rename file (or directory)
		boolean success = file.renameTo(file2);
		return success;
	}

	public final String getDir() {
		return dir;
	}

	public final boolean setDir(String dir) {
		File afile = new File(getFullPath());
		this.dir = dir;
		if (afile.renameTo(new File(dir + name))) {
			System.out.println("File is moved successful!");
			return true;
		} else {
			System.out.println("File is failed to move!");
			return false;
		}
	}

	public final String getFullPath() {
		return dir + name + type;
	}

	public final Pointer getFilePointer() {
		return filePointer;
	}

	public final void setFilePointer(Pointer filePointer) {
		this.filePointer = filePointer;
	}

}
