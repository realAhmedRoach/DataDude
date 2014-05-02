package org.dataman.security;

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public final class PasswordEncryptor {

	private SecretKeySpec key;
	private Cipher aes;

	public PasswordEncryptor(String passphrase) {

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
