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

package org.datadude.security;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.logging.*;

import org.datadude.*;

public class User implements Serializable {
	private static final long serialVersionUID = 254827L;

	private String name;
	private String user;
	private final String password;
	private final char[] passArray;
	private byte[] passBytes;
	private byte[] salt;

	private String userFolder;

	private boolean encrypted = false;

	private transient StringEncryptor enc = new StringEncryptor();

	public User(String userName, char[] pass) {

		user = userName;
		password = new String(pass);
		passArray = pass;
		passBytes = password.getBytes();
		try {
			salt = StringEncryptor.generateSalt();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
		}

		userFolder = null;

	}

	public void encrypt() {
		passBytes = enc.encryptPassword(password, salt);
		encrypted = true;
	}

	public boolean check(String str) {
		if (encrypted) {
			try {
				enc = new StringEncryptor();
				return enc.authenticate(str, passBytes, salt);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
				Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		} else {
			System.out.println("Password not encrypted!");
			return false;
		}
	}

	public static boolean check(User s, String str) {
		return s.check(str);
	}

	public void save() throws IOException {
		if (encrypted) {
			File f = new File(DataDude.getPassLoc() + getUserName() + ".ser");
			// f.getParentFile().mkdirs();
			f.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + DataDude.getPassLoc() + getUserName() + ".ser");
		} else {
			throw new RuntimeException("User's Password not encrypted");
		}
	}

	public String getUserFolder() {
		return userFolder;
	}

	public void setUserFolder(String _userFolder) {
		try {
			this.userFolder = _userFolder;
			if (!new File(this.userFolder).exists()) {
				new File(this.userFolder).mkdirs();
			}
		} catch (NullPointerException e) {
			setUserFolder(System.getProperty("user.home") + File.separator + "DataDude Users" + File.separator
					+ getUserName());
		} finally {
			System.gc();
		}
	}

	/**
	 * Gets the username of this user
	 * 
	 * @return the username
	 */
	public String getUserName() {
		return user;
	}

	/**
	 * Sets the username of this user This gets used when the save function gets
	 * called
	 * 
	 * @param username the new username
	 */
	public void setUserName(String username) {
		user = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public char[] getPasswordArray() {
		return passArray;
	}

	public byte[] getPasswordBytes() {
		return passBytes;
	}
}
