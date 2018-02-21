package btc.blockchain.security;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;


public class SHA256 implements Serializable {

	private static final long serialVersionUID = -3333000894287105224L;

	private static final String salt = "!@#salt[$^";

	private SHA256() { }


	public static String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(input.getBytes());

		return bytesToHex(messageDigest.digest());
	}

	public static String random() throws NoSuchAlgorithmException {
		return hash(String.format("%s%s%s", new Date().getTime(), salt, new Random().nextInt(988887)));
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) {
			result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
}
