package org.dataman.datamanaging;

import java.io.File;
import java.util.*;

public class Operators {

	private DataManFile file = null;

	public Operators(DataManFile _file){
		file = _file;
	}
	
	public boolean write(ArrayList<String> data){
		
		File fileOp = new File(file.getFullPath());
		return true;
	}
}
