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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileOperator {

	private DataDudeFile file;

	/**
	 * Creates a new FileOperator with the specified file
	 * @param _file The file to use for operations.
	 */
	public FileOperator(DataDudeFile _file) {
		file = _file;
	}

	/**
	 * Writes an ArrayList into the file.
	 * @param data The data to write
	 * @param append Determine whether to append to the file or overwrite.
	 * @return
	 */
	public boolean write(ArrayList<String> data, boolean append) {

		File fileOp = new File(file.getFullPath());
		System.out.println("Saving " + getClass().getName());

		try {
			if (!fileOp.exists()) {
				fileOp.createNewFile();
				System.out.println("File not found.");
			} else {
				System.out.println("Found file");
			}

			BufferedWriter write = new BufferedWriter(new FileWriter(fileOp,
					append));

			for (int i = 0; i < data.size(); i++) {
				write.write(data.get(i));
				write.newLine();
			}

			write.close();
			write = null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Loads a file into an ArrayList
	 * @return An ArrayList of Strings that has all the lines of the loaded file
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> loadFile() throws FileNotFoundException {
		File fileOp = new File(file.getFullPath());
		ArrayList<String> data = new ArrayList<String>();

		if (!fileOp.exists()) {
			throw new FileNotFoundException("Can't load file: File not found");
		} else {
			try {
				BufferedReader read = new BufferedReader(new FileReader(fileOp));

				String line = "";
				while ((line = read.readLine()) != null) {
					data.add(line);
				}
				
				read.close();
				read = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
