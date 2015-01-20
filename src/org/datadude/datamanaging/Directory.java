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

import java.io.File;

/**
 * A class that represents
 * a directory.
 * @author aarh9
 */
public class Directory {

	/**
	 * The path of this directory
	 */
	public String path;

	public Directory() {
		this.path = System.getProperty("user.home");
	}

	/**
	 * Creates a new Directory with {@link File} Object
	 * @param _path The file to use as the path
	 */
	public Directory(File _path) {
		if (_path == null) throw new NullPointerException("Directory's path can't be null");
		if (_path.isDirectory())
			this.path = _path.getAbsolutePath();
		else
			this.path = System.getProperty("user.home");

	}

	/**
	 * Creates a new Directory with a String
	 * @param path The string to use as path
	 */
	public Directory(String path) {
		File temp = new File(path);
		if (temp.isDirectory()) 
				this.path = path;
		else {
			temp.mkdirs();
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
