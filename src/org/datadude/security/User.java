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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.crypto.Cipher;

import org.datadude.DataDude;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class User implements Serializable {
	private static final long serialVersionUID = 254827L;

	private String name;
	private String user;
	private String password;
	private char[] passArray;
	private byte[] passBytes;
	private Cipher c;
	
	private String userFolder;

	private boolean encrypted = false;

	private transient StrongPasswordEncryptor enc = new StrongPasswordEncryptor();

	public User(String userName, char[] pass) {

		user = userName;
		password = new String(pass);
		passArray = pass;
		passBytes = password.getBytes();

		userFolder = null;

	}

	public void encrypt() {
		password = enc.encryptPassword(password);
		encrypted = true;
	}

	public boolean check(String str) {
		if (encrypted) {
			enc = new StrongPasswordEncryptor();
			return enc.checkPassword(str, password);
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
			f.mkdirs();
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

	public String getUserName() {
		return user;
	}

	public void setUserName(String name) {
		user = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return new String(password);
	}

	public char[] getPasswordArray() {
		return passArray;
	}

	public byte[] getPasswordBytes() {
		return passBytes;
	}

	/*public void decrypt() {
		byte[] decrypted;
		try {
			c.init(Cipher.DECRYPT_MODE, key, ivSpec);
			decrypted = new byte[c.getOutputSize(enc_len)];
			dec_len = c.update(encrypted, 0, enc_len, decrypted, 0);
			dec_len += c.doFinal(decrypted, dec_len);
			passBytes = decrypted;
			password = new String(decrypted);
			passArray = password.toCharArray();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}*/

}
