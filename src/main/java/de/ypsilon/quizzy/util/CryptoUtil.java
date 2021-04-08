package de.ypsilon.quizzy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class CryptoUtil {

    /**
     * Hash data with a provided salt.
     *
     * @param data the data to hash
     * @param salt the salt to use.
     * @return the hash.
     */
    public static byte[] hash(byte[] data, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(salt);
            return md.digest(data);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cipher not available.");
        }
    }

    /**
     * Generate a new salt for use in the hash method.
     *
     * @return a byte[] that is securely generated.
     * @throws NoSuchAlgorithmException when the algorithm is invalid.
     * @throws NoSuchProviderException the provided is invalid.
     */
    public static byte[] generateSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
