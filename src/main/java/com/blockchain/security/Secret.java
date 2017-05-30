package com.blockchain.security;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;


public class Secret {
	private final static Logger logger = Logger.getLogger(Secret.class);

    private static final String ALGORITHM = "AES";
    
    
    public static JsonObject encrypt(String text) {
    	BKey bKey = getBKey(0);
    	byte[] encryptionKey = bKey.getValue().getBytes(StandardCharsets.UTF_8);
    	byte[] plainText     = text.getBytes(StandardCharsets.UTF_8);
    	
    	byte[] cipherByte = exec(Cipher.ENCRYPT_MODE, plainText, encryptionKey);
    	
    	JsonObject jsonObj = null;
    	try { 	
    		String cipherText = new String(cipherByte, "ISO-8859-1");
    		
    		jsonObj = new JsonObject();
    		jsonObj.addProperty("cipher_text",    cipherText);
        	jsonObj.addProperty("blockchain_key", bKey.getKey());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
    	return jsonObj;
    }

    public static String decrypt(String text, int key) {  
    	BKey bKey = getBKey(key);
		try {
			byte[] cipherText = text.getBytes("ISO-8859-1");
			byte[] encryptionKey = bKey.getValue().getBytes(StandardCharsets.UTF_8);
			return new String(exec(Cipher.DECRYPT_MODE, cipherText, encryptionKey));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		} 
    	return null;
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
		if(key > 0 && key <= BKey.values().length){
			return BKey.getByKey(key);
		}
		List<BKey> keys = Collections.unmodifiableList(Arrays.asList(BKey.values()));
		return keys.get(new Random().nextInt(keys.size()));
	}
}
