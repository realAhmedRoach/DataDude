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

public class FileOperator {

	private DataDudeFile file;

	public FileOperator(DataDudeFile _file) {
		file = _file;
	}

	public boolean write(ArrayList<String> data, boolean overWrite) {

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
					!overWrite));

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

	public ArrayList<String> loadFile() {
		File fileOp = new File(file.getFullPath());
		ArrayList<String> data = new ArrayList<String>();

		if (!fileOp.exists()) {
			System.out.println("Can't load file: File not found");
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
