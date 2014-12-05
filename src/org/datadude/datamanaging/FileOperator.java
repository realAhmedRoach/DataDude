package org.datadude.datamanaging;

import java.io.*;
import java.util.*;

public class FileOperator {

	private DataManFile file = null;

	public FileOperator(DataManFile _file) {
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
