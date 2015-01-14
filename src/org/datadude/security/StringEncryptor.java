/**
 * *****************************************************************************
 * DataDude is a data managing application designed to have many types of data
 * in one application Copyright (C) 2015 Ahmed R. (theTechnoKid)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * *****************************************************************************
 */
package org.datadude.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * A class that can encrypt and authenticate Strings using the PBKDF2 with SHA1
 * algorithm. Useful for storing passwords.
 *
 * @author theTechnoKid
 */
public final class StringEncryptor {

    /**
     * @param password the password to encrypt
     * @param salt the salt to encrypt the password
     * @return the encrypted password in bytes
     */
    public byte[] encryptPassword(String password, byte[] salt) {
        try {
            // PBKDF2 with SHA-1 as the hashing algorithm.
            String algorithm = "PBKDF2WithHmacSHA1";
            // SHA-1 generates 160 bit hashes, so that's what makes sense here
            int derivedKeyLength = 160;
            int iterations = 20000;

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Authenticates the <code>attemptedPassword</code> by
     * encrypting it with the same salt and seeing if they both
     * are equal.
     * @param attemptedPassword the attempted password to be checked
     * @param encryptedPassword the encrypted password to check against
     * @param salt the <code>encryptedPassword</code>s salt
     * @return whether the <code>attemptedPassword</code> is correct
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        byte[] encryptedAttemptedPassword = encryptPassword(attemptedPassword, salt);

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    /**
     * Generates a random salt to be used for
     * encrypting passwords
     * @return a random salt
     * @throws NoSuchAlgorithmException 
     */
    public static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[10];
        r.nextBytes(salt);
        return salt;
    }

}
