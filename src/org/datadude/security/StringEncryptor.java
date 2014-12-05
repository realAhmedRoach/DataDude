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
