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
package org.datadude.nodes;

/**
 * @author theTechnoKid
 *	All nodes extend this interface
 */
public interface Node {
	/**
	 * Where all the initialization is for the Node
	 */
	void init();
	/**
	 * Saves the contents of the node
	 * @param file the name of the file to save
	 * @return success of the save
	 */
	boolean save(String file);
	/**
	 * Loads contents of a file
	 * @param file the file to load
	 * @return success of the load
	 */
	boolean load(String file);
	/*
	 * Performs cleanup
	 * processes for that node
	 */
	void cleanup();
}
