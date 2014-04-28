package org.dataman.datamanaging;

import java.io.*;
import java.util.*;

public class Operators {

	private DataManFile file = null;

	public Operators(DataManFile _file) {
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

			BufferedWriter write = new BufferedWriter(new FileWriter(fileOp, !overWrite));
			
			for (int i = 0; i < data.size(); i++) {
				write.write(data.get(i));
				write.newLine();
			}
				
			write.close();
			write = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
