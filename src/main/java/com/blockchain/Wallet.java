package com.blockchain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

public class Wallet {
	
	public static void main(String[] args){
		//JsonObject teste = create(args[1], args[2],args[3], args[4], args[5], args[6]);
		System.out.println("teste");
	}
 
	public static JsonObject create (String serviceURL, String password, String apiCode) {
		return create(serviceURL, password, apiCode, null, null, null);
	}
	
	public static JsonObject create (String serviceURL, String password,String apiCode, String privateKey, String label, String email) {
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
		
		try {
			response = HttpClient.getInstance().post(serviceURL, "api/v2/create", params);
		} catch (APIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonObject jsonObj = null;
		
		if(response != null){
			jsonObj = new JsonParser().parse(response).getAsJsonObject();
		}
		
		return jsonObj;
	}
}
