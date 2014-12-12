package org.datadude.datamanaging;

/**
 * @author aarh9
 */
public class CSVParser {
	private String line;
	private String[] parsedLine;
	
	/**
	 * Create the parser
	 * @param _line the string to be parsed.
	 */
	public CSVParser(String _line) {
		line = _line;
	}
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String[] getParsedLine() {
		return parsedLine;
	}

	public void setParsedLine(String[] parsedLine) {
		this.parsedLine = parsedLine;
	}

	public String[] parse() {
		parsedLine = line.split(",");
		return parsedLine;
	}
}
