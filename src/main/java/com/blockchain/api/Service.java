package com.blockchain.api;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.blockchain.api.HttpClient;

public class Service {
	
	private final static Logger logger = Logger.getLogger(Service.class);
	
	private final static String serviceURL = "http://localhost:3000/";
	
	private final static JsonParser jsonParser = new JsonParser();
	
	private static Service instance;
	
	private Service() {}
	
	public static Service getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (Service.class) {
			if (instance == null) {
				instance = new Service();
			}
		}
		return instance;
	}
	
	protected JsonObject post(String uri, Map<String, String> params){
		String response = null;
		try {
			response = HttpClient.getInstance().post(serviceURL, uri, params);
			return jsonParser.parse(response).getAsJsonObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
        return null;
	}
	
	protected JsonObject get(String uri, Map<String, String> params){
		String response = null;
		try {
			response = HttpClient.getInstance().get(serviceURL, uri, params);
			return jsonParser.parse(response).getAsJsonObject();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
        return null;
	}
}
