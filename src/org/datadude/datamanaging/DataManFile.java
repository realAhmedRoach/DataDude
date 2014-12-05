package org.datadude.datamanaging;

import java.io.*;

public class DataManFile implements Serializable {

	private static final long serialVersionUID = 730623L;

	public static final String T_TEXT = "txt";
	public static final String T_TABLE = "dmt";
	public static final String T_DIAGRAM = "dmd";
	public static final String T_PROPERTIES = "properties";
	public static final String T_OTHER = "dmf";

	protected String type;
	protected String dir;
	protected String name;
	protected Pointer filePointer;

	public DataManFile(String ext, String name, String dir) {
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
		if (file2.exists()) throw new java.io.IOException("file exists");

		// Rename file (or directory)
		boolean success = file.renameTo(file2);
		if (!success) {
			return false;
		}
		return true;
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
		return dir+name+type;
	}
	
	public final Pointer getFilePointer() {
		return filePointer;
	}

	public final void setFilePointer(Pointer filePointer) {
		this.filePointer = filePointer;
	}

}
