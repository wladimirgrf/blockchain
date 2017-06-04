package com.blockchain.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

public class Password {
	private final static Logger logger = Logger.getLogger(Password.class);
	
	private final static String salt = "!@#block[$^";
	
	private static final String ALGORITHM = "MD5";

	private Password() {}
	
	public static String create() {
        String password = String.format("%s%s%s", new Date(), salt, new Random().nextInt(99777));
        try {
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
            md5.update(password.getBytes(), 0, password.length());
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
        return null;
	}
}
