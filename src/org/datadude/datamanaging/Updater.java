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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Updater for DataDude This checks if the version on the update database is the
 * same as the current version. If not, it downloads the file.
 *
 * @author theTechnoKid
 */
public class Updater {
	private static final String update = "https://thetechnokid.github.io/DataDude/update.html";

	private final static ArrayList<String> data;
	static {
		data = getData();
	}

	public static String getVersion() throws IOException {
		String version = data.get(0);
		return version.substring(version.indexOf("[version]") + 9, version.indexOf("[/version]"));
	}

	public static String getWhatsNew() throws IOException {
		String wasnew = data.get(1);
		return wasnew.substring(wasnew.indexOf("[new]") + 5, wasnew.indexOf("[/new]"));
	}

	public static String getVersionNo() throws IOException {
		String vno = data.get(2);
		return vno.substring(vno.indexOf("[vno]") + 5, vno.indexOf("[/vno]"));
	}

	public static String getDownload() throws IOException {
		String downLoc = data.get(3);
		return downLoc.substring(downLoc.indexOf("[download]") + 10, downLoc.indexOf("[/download]"));
	}

	private static ArrayList<String> getData() {
		try {
			URL url = new URL(update);
			InputStream html = url.openStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(html));

			ArrayList<String> s = new ArrayList<String>();
			String line = null;
			while ((line = r.readLine()) != null)
				s.add(line);
			return s;
		} catch (IOException e) {
			return null;
		}
	}

	public static void download() {
		try {
			URL url = new URL(getDownload());
			String localFilename = "dd_" + getVersionNo() + ".jar";
			Utils.downloadFromUrl(url, localFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void run() throws IOException {
		String[] run = { "java", "-jar", "dd_" + getVersionNo() + ".jar" };

		Runtime.getRuntime().exec(run);

		System.exit(0);
	}
}
