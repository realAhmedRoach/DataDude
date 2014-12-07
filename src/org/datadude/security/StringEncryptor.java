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

package org.datadude.security;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public final class StringEncryptor {
	private SecretKeySpec key;
	private Cipher aes;

	public StringEncryptor(String passphrase) {

		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance("SHA");
			digest.update(passphrase.getBytes());
			SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
			this.key = key;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean checkPassword(String passToBeChecked, String _pass) {
		String pass = decryptPassword(_pass);
		return passToBeChecked == pass;
	}
	
	public String encryptPassword(String pass) {
		try {
			aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aes.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = aes.doFinal(pass.getBytes());
			return new String(ciphertext);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String decryptPassword(String ciphertext) {
		try {
			aes.init(Cipher.DECRYPT_MODE, key);
			String cleartext = new String(aes.doFinal(ciphertext.getBytes()));
			return cleartext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
