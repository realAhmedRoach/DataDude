/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    This program is free software: you can redistribute it and/or modify
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
import java.util.*;
//import java.nio.file.*;

public class Database {

	Directory directory;
	Pointer dirPointer;

	ArrayList<DataDudeFile> files;
	
	/**
	 * 
	 * @param path the databases path
	 */
	public Database(String path) {
		directory = new Directory();
		dirPointer = new Pointer(Pointer.DIRECTORY_POINTER);
		dirPointer.setCurrFile(new File(path));
		files = new ArrayList<DataDudeFile>();
				
	}

	/**
	 * 
	 * @param theFile file to be added
	 * @return {@code true} if added successfully
	 */
	public boolean addFile(DataDudeFile theFile) {
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