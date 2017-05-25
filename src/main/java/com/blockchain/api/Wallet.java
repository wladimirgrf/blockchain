package com.blockchain.api;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;


public class Wallet {	
	private final static Service service = Service.getInstance();
	
	private static Wallet instance;
	
	
	public static void main(String[] args){
		//JsonObject teste = create("123", "cd0ebd41-3673-4096-8698-fdf738dbdf2e", null, "testeME", null);
		JsonObject teste = balance("c7d41c0a-1e60-4a62-8826-23992bc2d2c7", "123");
		System.out.println(teste);
		System.out.println("teste");
	
	}
	
	private Wallet() {}
	
	public static Wallet getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (Wallet.class) {
			if (instance == null) {
				instance = new Wallet();
			}
		}
		return instance;
	}
 
	public static JsonObject create (String password, String apiCode) {
		return create(password, apiCode, null, null, null);
	}
	
	public static JsonObject create (String password,String apiCode, String privateKey, String label, String email) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("password", password);
		params.put("api_code", apiCode);
		
		if (privateKey != null) {
			params.put("priv", privateKey);
		}
		if (label != null) {
			params.put("label", label);
		}
		if (email != null) {
			params.put("email", email);
		}
		
		return service.post("api/v2/create", params);
	}
	
	public static JsonObject balance (String guid, String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("password", password);
		
		String uri = String.format("merchant/%s/balance", guid);
		return service.get(uri, params);
    }
}
