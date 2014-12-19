package org.datadude.nodes;

/**
 * @author aarh9
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
	
}
