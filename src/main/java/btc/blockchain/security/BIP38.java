package btc.blockchain.security;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;

import com.google.bitcoin.core.AddressFormatException;
import com.google.bitcoin.core.Base58;
import com.google.bitcoin.core.DumpedPrivateKey;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.params.MainNetParams;
import com.google.bitcoin.params.TestNet3Params;

import com.lambdaworks.crypto.SCrypt;

public class BIP38 {

	private static NetworkParameters params = TestNet3Params.get();


	public static void main(String args[])  {
		
		System.out.println("");
		
		JSONObject ek = encrypt("cPvMdgufqvNwZMntSX2iQJFiyGnii9A9vpfEyeeFxgRPy7tzMT3U");
		System.out.println(ek);
		
		System.out.println(decrypt((String) ek.get("bip38_cipher"),(int) ek.get("bip38_key")));
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject encrypt(String privateKey) {
		JSONObject jsonObj = new JSONObject();
		BKey bKey = getBKey(0);
		try {
			jsonObj.put("bip38_cipher", bip38Encrypt(bKey.getValue(), privateKey));
			jsonObj.put("bip38_key", bKey.getKey());
		} catch (Exception e) {
			jsonObj.put("error", e.getMessage());
		} 
		return jsonObj;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject decrypt(String bip38Cipher, int bip38Key) {
		JSONObject jsonObj = new JSONObject();
		BKey bKey = getBKey(bip38Key);
		try {
			jsonObj.put("private_key", bip38Decrypt(bKey.getValue(), bip38Cipher));
		} catch (Exception e) {
			jsonObj.put("error", e.getMessage());
		} 
		return jsonObj;
	}

	private static String bip38Encrypt(String passphrase, String encodedPrivateKey) throws AddressFormatException, UnsupportedEncodingException, GeneralSecurityException {	
		DumpedPrivateKey dk = new DumpedPrivateKey(params, encodedPrivateKey);
		ECKey ktmp = dk.getKey();
		
		byte[] keyBytes = ktmp.getPrivKeyBytes();
		
		ECKey key = new ECKey(new BigInteger(1, keyBytes), null);
		String address = key.toAddress(params).toString();
		
		byte[] tmp = address.getBytes("ASCII");
		byte[] hash = doubleHash(tmp, 0, tmp.length);
		byte[] addressHash = Arrays.copyOfRange(hash, 0, 4);
		byte[] scryptKey = SCrypt.scrypt(passphrase.getBytes("UTF8"), addressHash, 16384, 8, 8, 64);
		byte[] derivedHalf1 = Arrays.copyOfRange(scryptKey, 0, 32);
		byte[] derivedHalf2 = Arrays.copyOfRange(scryptKey, 32, 64);
		byte[] k1 = new byte[16];
		byte[] k2 = new byte[16];
		
		for (int i = 0; i < 16; i++) {
			k1[i] = (byte) (keyBytes[i] ^ derivedHalf1[i]);
			k2[i] = (byte) (keyBytes[i+16] ^ derivedHalf1[i+16]);
		}

		byte[] encryptedHalf1 = AESEncrypt(k1, derivedHalf2);
		byte[] encryptedHalf2 = AESEncrypt(k2, derivedHalf2);
		byte[] header = { 0x01, 0x42, (byte) 0xe0 };
		byte[] encryptedPrivateKey = concat(header, addressHash, encryptedHalf1, encryptedHalf2);

		return base58Check(encryptedPrivateKey);
	}

	private static String bip38Decrypt(String passphrase, String encryptedKey) throws AddressFormatException, UnsupportedEncodingException, GeneralSecurityException {
		byte[] encryptedKeyBytes = Base58.decodeChecked(encryptedKey);

		byte[] addressHash =  Arrays.copyOfRange(encryptedKeyBytes, 3, 7);
		byte[] scryptKey = SCrypt.scrypt(passphrase.getBytes("UTF8"), addressHash, 16384, 8, 8, 64);
		byte[] derivedHalf1 = Arrays.copyOfRange(scryptKey, 0, 32);
		byte[] derivedHalf2 = Arrays.copyOfRange(scryptKey, 32, 64);
		byte[] encryptedHalf1 = Arrays.copyOfRange(encryptedKeyBytes, 7, 23);
		byte[] encryptedHalf2 = Arrays.copyOfRange(encryptedKeyBytes, 23, 39);
		byte[] k1 = AESDecrypt(encryptedHalf1, derivedHalf2);
		byte[] k2 = AESDecrypt(encryptedHalf2, derivedHalf2);
		byte[] keyBytes = new byte[32];
		
		for (int i = 0; i < 16; i++) {
			keyBytes[i] = (byte) (k1[i] ^ derivedHalf1[i]);
			keyBytes[i + 16] = (byte) (k2[i] ^ derivedHalf1[i + 16]);
		}

		boolean compressed = (encryptedKeyBytes[2] & (byte) 0x20) == 0x20;
		ECKey k = new ECKey(new BigInteger(1, keyBytes), null, compressed);
		
		return k.getPrivateKeyEncoded(params).toString();
	}
	
	
	private static BKey getBKey(int key){
		if(key > 0 && key <= BKey.values().length){
			return BKey.getByKey(key);
		}
		List<BKey> keys = Collections.unmodifiableList(Arrays.asList(BKey.values()));
		return keys.get(new Random().nextInt(keys.size()));
	}

	private static byte[] doubleHash(byte[] data, int off, int len) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data, off, len);
		return md.digest(md.digest());
	}

	private static String base58Check(byte [] b) throws NoSuchAlgorithmException {
		byte[] r = new byte[b.length + 4];
		System.arraycopy(b, 0, r, 0, b.length);
		System.arraycopy(doubleHash(b, 0, b.length), 0, r, b.length, 4);
		return Base58.encode(r);
	}

	private static byte[] AESEncrypt(byte[] plaintext, byte[] key) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		Key aesKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		return cipher.doFinal(plaintext);
	}

	private static byte[] AESDecrypt(byte[] ciphertext, byte[] key) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		Key aesKey = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		return cipher.doFinal(ciphertext);
	}

	private static byte[] concat(byte[]... buffers) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (byte [] b : buffers) {
			try {
				baos.write(b);
			}
			catch (java.io.IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return baos.toByteArray();
	}
}
