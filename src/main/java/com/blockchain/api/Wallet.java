package com.blockchain.api;

import java.util.HashMap;
import java.util.Map;

import com.blockchain.security.Password;
import com.blockchain.security.Secret;
import com.google.gson.JsonObject;


public class Wallet {		

	public static void main(String[] args){
		//JsonObject teste = create("123", "cd0ebd41-3673-4096-8698-fdf738dbdf2e", null, "testeME", null);
		//JsonObject teste = balance("c7d41c0a-1e60-4a62-8826-23992bc2d2c7", "123");

		System.out.println("teste");
	
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
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("api_code", apiCode);

		if(password == null || password.isEmpty()){
			password = Password.create();
		}
		
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
		params.put("password", Secret.decrypt(password, key));
		
		String uri = String.format("merchant/%s/balance", guid);
		return Service.get(uri, params);
    }
}
