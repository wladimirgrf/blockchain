package btc.blockchain.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class Password {

	private Password() { }

	public static String create() throws NoSuchAlgorithmException {
		String password = String.format("%s%s%s", new Date().getTime(), "!@#block[$^", new Random().nextInt(988887));
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(password.getBytes(), 0, password.length());
		return new BigInteger(1, md5.digest()).toString(16);
	}
}
