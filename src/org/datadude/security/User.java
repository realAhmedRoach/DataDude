package org.datadude.security;

import java.io.*;

import org.datadude.DataDude;
import org.jasypt.util.password.*;

public class User implements Serializable {
	private static final long serialVersionUID = 254827L;

	private String name;
	private String user;
	private String password;
	private char[] passArray;
	private byte[] passBytes;
	
	private String userFolder;

	private boolean encrypted = false;

	private transient StrongPasswordEncryptor enc = new StrongPasswordEncryptor();

	public User(String userName, char[] pass) {

		user = userName;
		password = new String(pass);
		passArray = pass;
		passBytes = new String(password).getBytes();
		
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
			File f = new File(DataDude.getPassLoc()+getUserName()+".ser");
			f.mkdirs();
			f.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in "+ DataDude.getPassLoc() +
					 getUserName() + ".ser");
		} else {
			throw new RuntimeException("User's Password not encrypted");
		}
	}

	public String getUserFolder() {
		return userFolder;
	}

	public void setUserFolder(String userFolder) {
		this.userFolder = userFolder;
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

	/*
	 * public void decrypt() { try { c.init(Cipher.DECRYPT_MODE, key, ivSpec);
	 * decrypted = new byte[c.getOutputSize(enc_len)]; dec_len =
	 * c.update(encrypted, 0, enc_len, decrypted, 0); dec_len +=
	 * c.doFinal(decrypted, dec_len); passBytes = decrypted; password = new
	 * String(decrypted); passArray = password.toCharArray(); }catch(Exception
	 * e) { e.printStackTrace(); return; } }
	 */

}
