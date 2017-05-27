package com.blockchain.security;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


public class Secret {
	private final static Logger logger = Logger.getLogger(Secret.class);

    private static final String ALGORITHM = "AES";
   
    private static BKey bKey;

    public static void main(String[] args) {
    	String text = "KwrtihoX18TKbK1gStHUYpMTzXJLNY3Sgxs3LVRDDkDX2dEAGRST";
    	
    	//byte[] ct = encrypt(text);
    	
    	//System.out.println(ct);

    	//String dec = decrypt(ct, k);    	
    	
    	//System.out.println(dec);
    	

    	byte[] teste = "1".getBytes();
    	
    	System.out.println(new String(teste));
    }
    
    public static byte[] encrypt(String text) {
    	bKey = getBKey(0);
    	byte[] encryptionKey = bKey.getValue().getBytes(StandardCharsets.UTF_8);
    	byte[] plainText     = text.getBytes(StandardCharsets.UTF_8);
    	
    	return exec(Cipher.ENCRYPT_MODE, plainText, encryptionKey);
    }

    public static String decrypt(byte[] text, int key) {  
    	bKey = getBKey(key);
    	byte[] encryptionKey = bKey.getValue().getBytes(StandardCharsets.UTF_8);
    	
    	return new String(exec(Cipher.DECRYPT_MODE, text, encryptionKey));
    }
    
    private static byte[] exec(int mode, byte[] text, byte[] key) {
    	try{
    		Cipher cipher = Cipher.getInstance(ALGORITHM);
	    	SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
	    	
	    	cipher.init(mode, secretKey);
	    	
	    	return cipher.doFinal(text);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
		}
    	return null;
    }

	private static BKey getBKey(int key){
		if(key <= 0 || key > BKey.values().length){
			List<BKey> keys = Collections.unmodifiableList(Arrays.asList(BKey.values()));
			return keys.get(new Random().nextInt(keys.size()));
		}
		return BKey.getByKey(key);
	}
}
