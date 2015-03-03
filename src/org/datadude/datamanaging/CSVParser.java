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

	public String[] parse() {
		parsedLine = line.split("\\s*,\\s*");
		return parsedLine;
	}
}
