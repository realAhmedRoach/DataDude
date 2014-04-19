package org.dataman.datamanaging;

import java.io.*;
import java.util.*;
//import java.nio.file.*;

public class Database {

	Directory directory;
	Pointer dirPointer;

	ArrayList<DataManFile> files;
	
	/**
	 * 
	 * @param path the databases path
	 */
	public Database(String path) {
		directory = new Directory();
		dirPointer = new Pointer(Pointer.DIRECTORY_POINTER);
		dirPointer.setCurrFile(new File(path));
		files = new ArrayList<DataManFile>();
				
	}

	/**
	 * 
	 * @param theFile file to be added
	 * @return {@code true} if added successfully
	 */
	public boolean addFile(DataManFile theFile) {
		files.add(theFile);
		
		return true;
	}
	
	/*
	 * Getters And Setters
	 */
	
	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = new Directory(directory);
	}

	public Pointer getDirPointer() {
		return dirPointer;
	}

	public void setDirPointer(Pointer dirPointer) {
		this.dirPointer = dirPointer;
	}

}
