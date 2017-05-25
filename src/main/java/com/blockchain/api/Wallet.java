package com.blockchain.api;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.blockchain.api.HttpClient;

public class Wallet {
	
	private final static Logger logger = Logger.getLogger(Wallet.class);
	
	private static final String serviceURL = "http://localhost:3000/";
	
	private static Wallet instance;
	
	
	public static void main(String[] args){
		//JsonObject teste = create("123", "cd0ebd41-3673-4096-8698-fdf738dbdf2e", null, "testeME", null);
		JsonObject teste = balance("c7d41c0a-1e60-4a62-8826-23992bc2d2c7", "123");
		System.out.println(teste);
		System.out.println("teste");
		
		//boolean reachable = InetAddress.getByName(serviceURL).isReachable(2000);
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

		String response = null;
		JsonObject jsonObj = null;
		
		try {
			response = HttpClient.getInstance().post(serviceURL, "api/v2/create", params);
			jsonObj = new JsonParser().parse(response).getAsJsonObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return jsonObj;
	}
	
	public static JsonObject balance (String guid, String password) {
		Map<String, String> params = new HashMap<String, String>();
		String response    = null;
		JsonObject jsonObj = null;
		params.put("password", password);
		try {
			String uri = String.format("merchant/%s/balance", guid);
			response = HttpClient.getInstance().get(serviceURL, uri, params);
			jsonObj = new JsonParser().parse(response).getAsJsonObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		return jsonObj;
    }
}
