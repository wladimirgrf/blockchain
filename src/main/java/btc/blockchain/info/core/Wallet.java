package btc.blockchain.info.core;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import btc.blockchain.security.Secret;


public class Wallet {

	public static void main(String[] args){
		//JsonObject teste = create("123", "cd0ebd41367340968698fdf738dbdf2e", null, "testeME", null);
		//JsonObject teste = balance("c7d41c0a1e604a62882623992bc2d2c7", "123");
		//JsonObject teste = create(null, "cd0ebd41367340968698fdf738dbdf2e", "KyotvhGiw1KwDY9ftaTmx6ugk833f6euExXFBMReq6Q2xFkJwbwx", null, null);
		JsonObject teste = balance("0b68739394604eb6a209807c8ffb27fe", "FUSXcYD41T7BzASv+KjqaHDmmLqt/W/iAPgDfzP63lCYooLwOAgKQ8IcISEQL439HdL/Tql+za0PZdrEa4ACpg==", 12);

		System.out.println(teste);

	}

	private Wallet() { }

	public static JsonObject create (String password, String apiCode) {
		return create(password, apiCode, null, null, null);
	}


	public static JsonObject create (String password, String apiCode, String privateKey, String label, String email) {
		JsonObject jsonObj = new JsonObject();

		if(apiCode == null || apiCode.isEmpty()){
			jsonObj.addProperty("error", "api code is missing!");
			return jsonObj;
		}

		if(password == null || password.isEmpty()){
			jsonObj.addProperty("error", "password is missing!");
			return jsonObj;
		}

		Map<String, String> params = new HashMap<String, String>();

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

		JsonObject encryption = Secret.encrypt(password);

		jsonObj.addProperty("password_enc", encryption.get("cipher_text").getAsString());
		jsonObj.addProperty("password_key", encryption.get("key").getAsInt());

		return jsonObj;
	}

	public static JsonObject balance (String guid, String password, int key) {
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
		
		if(password_dec.get("plain_text") != null) {
			params.put("password", password_dec.get("plain_text").toString());
		} else {
			jsonObj.addProperty("error", password_dec.get("error").toString());
			return jsonObj;
		}

		String uri = String.format("merchant/%s/balance", guid);
		return Service.get(uri, params);
	}
}
