package btc.blockchain.info.core;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import btc.blockchain.security.Password;
import btc.blockchain.security.Secret;


public class Wallet {
	
	private Wallet() { }

	public static JsonObject create (String apiCode, String privateKey) throws NoSuchAlgorithmException {
		return create(apiCode, privateKey, null, null);
	}

	public static JsonObject create (String apiCode, String privateKey, String label, String email) throws NoSuchAlgorithmException {
		JsonObject jsonObj = new JsonObject();

		if(apiCode == null || apiCode.isEmpty()){
			jsonObj.addProperty("error", "api code is missing!");
			return jsonObj;
		}

		Map<String, String> params = new HashMap<String, String>();
		String password = Password.create();

		params.put("api_code", apiCode);
		params.put("password", password);

		if (privateKey != null) {
			params.put("priv", privateKey);
		}
		if (label != null) {
			params.put("label", label);
		}
		if (email != null) {
			params.put("email", email);
		}

		jsonObj = Service.post("api/v2/create", params);

		if(jsonObj.get("error") != null) {
			return jsonObj;
		}
		
		JsonObject encryption = Secret.encrypt(password);

		jsonObj.addProperty("password_enc", encryption.get("cipher_text").getAsString());
		jsonObj.addProperty("password_key", encryption.get("key").getAsInt());

		return jsonObj;
	}
	
	private static long var=0;


	public static JsonObject balance (String guid, String password, int key) {
		var++;
		System.out.println("Test-"+var);
		JsonObject jsonObj = new JsonObject();

		if(guid == null || guid.isEmpty()){
			jsonObj.addProperty("error", "guid is missing!");
			return jsonObj;
		}
		if(password == null || password.isEmpty()){
			jsonObj.addProperty("error", "password is missing!");
			return jsonObj;
		}

		Map<String, String> params = new HashMap<String, String>();
		JsonObject password_dec = Secret.decrypt(password, key);
		
		if(password_dec.get("error") != null) {	
			jsonObj.addProperty("error", password_dec.get("error").getAsString());
			return jsonObj;
		} else {
			params.put("password", password_dec.get("plain_text").getAsString());
		}
		
		String uri = String.format("merchant/%s/balance", guid);
		return Service.get(uri, params);
	}
}
