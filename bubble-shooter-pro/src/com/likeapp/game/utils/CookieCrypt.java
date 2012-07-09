/**
 * 
 */
package com.likeapp.game.utils;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



/**
 * @author Jackyli
 *
 */
public class CookieCrypt {
	private byte[] desKey;

	public CookieCrypt(String desKey) {
		this.desKey = desKey.getBytes();
	}

	/**
	 * Cookie Encoder
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	/**
	 * Cookie Decoder
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String input) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result));
	}

	/**
	 * DES Encoder
	 * 
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	private byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;

		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);

		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	 * DES Decoder
	 * 
	 * @param encryptText
	 * @return
	 * @throws Exception
	 */
	private byte[] desDecrypt(byte[] encryptText) throws Exception {

		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DES");

		cipher.init(Cipher.DECRYPT_MODE, key, sr);

		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * Base64 Encode
	 * 
	 * @param s
	 * @return
	 */
	private static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		return Base64.encode(s);
	}

	/**
	 * Base64 Decode
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	private static byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;
		byte[] b = Base64.decode(s);
		return b;
	}

	public static void main(String[] args) throws Exception {
		String key = "12345678";
		String input = "wanikljdflk";
		CookieCrypt crypt = new CookieCrypt(key);
		System.out.println("Encode:" + crypt.encrypt(input));
		System.out.println("Decode:" + crypt.decrypt(crypt.encrypt(input)));

	}
}
