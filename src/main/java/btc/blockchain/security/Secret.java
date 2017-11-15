package btc.blockchain.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;


public class Secret {

	private static final String ALGORITHM = "AES";

	private static final String CHARSET = "ISO-8859-1";

	private Secret() { }
	
	private static byte[] exec(int mode, byte[] text, BKey bKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher 	 = Cipher.getInstance(ALGORITHM);
		byte[] encryptionKey = Base64.decodeBase64(bKey.getValue());
		SecretKeySpec secretKey = new SecretKeySpec(encryptionKey, ALGORITHM);
		cipher.init(mode, secretKey);
		return cipher.doFinal(text);
	}

	private static BKey getBKey(int key){
		if(key > 0 && key <= BKey.values().length){
			return BKey.getByKey(key);
		}
		List<BKey> keys = Collections.unmodifiableList(Arrays.asList(BKey.values()));
		return keys.get(new Random().nextInt(keys.size()));
	}

	public static JsonObject encrypt(String text) {
		BKey bKey = getBKey(0);
		JsonObject jsonObj = new JsonObject();
		try { 	
			byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
			byte[] cipherByte = exec(Cipher.ENCRYPT_MODE, plainText, bKey);
			
			String cipherText = Base64.encodeBase64String(cipherByte);
			jsonObj.addProperty("cipher_text", cipherText);
			jsonObj.addProperty("key", bKey.getKey());
		} catch (Exception e) {
			jsonObj.addProperty("error", e.toString());
		}
		return jsonObj;
	}

	public static JsonObject decrypt(String text, int key) {  
		BKey bKey = getBKey(key);
		JsonObject jsonObj = new JsonObject();
		try {
			byte[] cipherText = Base64.decodeBase64(text.getBytes(CHARSET));
			String plainText = new String(exec(Cipher.DECRYPT_MODE, cipherText, bKey));
			jsonObj.addProperty("plain_text", plainText);
		} catch (Exception e) {
			jsonObj.addProperty("error", e.toString());
		}
		return jsonObj;
	}
}
