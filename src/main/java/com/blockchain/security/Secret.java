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
    
    private static final String CHARSET = "ISO-8859-1";
    
	private Secret() { }
	
    
    public static JsonObject encrypt(String text) {
    	BKey bKey = getBKey(0);
    	byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
    	byte[] cipherByte = exec(Cipher.ENCRYPT_MODE, plainText, bKey);
    	JsonObject jsonObj = null;
    	try { 	
    		String cipherText = new String(cipherByte, CHARSET);
    		jsonObj = new JsonObject();
    		jsonObj.addProperty("cipher_text", cipherText);
        	jsonObj.addProperty("key", bKey.getKey());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
    	return jsonObj;
    }

    public static String decrypt(String text, int key) {  
    	BKey bKey = getBKey(key);
    	byte[] cipherText;
		try {
			cipherText = text.getBytes(CHARSET);
			return new String(exec(Cipher.DECRYPT_MODE, cipherText, bKey));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return null;
    }
    
    private static byte[] exec(int mode, byte[] text, BKey bKey) {
    	try{
    		Cipher cipher 	 = Cipher.getInstance(ALGORITHM);
    		byte[] encryptionKey 	= bKey.getValue().getBytes(StandardCharsets.UTF_8);
	    	SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, ALGORITHM);
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
