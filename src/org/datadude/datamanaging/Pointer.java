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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents either a file pointer or directory pointer
 * 
 * @author theTechnoKid
 * @since 0.01
 */
public class Pointer {

	int location;
	int type;
	File currFile;

	public static final int FILE_POINTER = 0;
	public static final int DIRECTORY_POINTER = 1;

	/**
	 * Makes a pointer with the specified parameters
	 * 
	 * @param file
	 *            the file to read from
	 * @param loc
	 *            the starting location of the pointer
	 * @param type
	 *            the type of pointer
	 */
	public Pointer(File file, int loc, int type) {
		this.currFile = file;
		this.location = loc;
		this.type = type;
	}

	public Pointer(int loc, int type) {
		this(null, 0, FILE_POINTER);
	}

	public Pointer(int type) {
		this(0, type);
	}

	/**
	 * Reads lines from the current file.
	 * 
	 * @param until
	 *            The stop line to read
	 * @return The lines that have been read.
	 * @throws IOException
	 *             If there is an error with reading.
	 */
	public ArrayList<String> readLines(int until) throws IOException {
		ArrayList<String> swag = new ArrayList<String>();
		if (this.type == FILE_POINTER) {
			BufferedReader br = new BufferedReader(new FileReader(currFile));

			if (location > 0)
				for (int i = 0; i < location; i++)
					br.readLine();
			if (until <= 0) {
				String line = null;
				while ((line = br.readLine()) != null)
					swag.add(line);
			} else {
				for (int y = 0; y < until; y++) {
					String line = null;
					while ((line = br.readLine()) != null)
						swag.add(line);
				}
			}
			br.close();
			return swag;
		} else {
			throw new IllegalStateException("Wrong pointer type.");
		}
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
