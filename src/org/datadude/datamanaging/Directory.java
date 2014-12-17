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

public class Directory {

	/**
	 * The path of this directory
	 */
	public String path;

	public Directory() {
		this.path = System.getProperty("user.home");
	}

	public Directory(File _path) {
		if (_path.isDirectory())
			this.path = _path.getAbsolutePath();
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
