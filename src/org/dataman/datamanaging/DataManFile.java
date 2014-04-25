package org.dataman.datamanaging;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class DataManFile implements Serializable {

	private static final long serialVersionUID = 7301413521671295623L;

	public static final String T_TEXT = "txt";
	public static final String T_TABLE = "dmt";
	public static final String T_DIAGRAM = "dmd";
	public static final String T_PROPERTIES = "properties";
	public static final String T_OTHER = "dmf";

	protected String type;
	protected String name;
	protected Pointer filePointer;

	public DataManFile(String ext, String name) {
		type = ext;
		this.name = name;
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

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDir() {
		return name;
	}

	public final void setDir(String name) {
		this.name = name;
	}

	public final Pointer getFilePointer() {
		return filePointer;
	}

	public final void setFilePointer(Pointer filePointer) {
		this.filePointer = filePointer;
	}

}
