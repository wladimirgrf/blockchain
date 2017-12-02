package btc.blockchain.info.core;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.blockchain.api.HttpClient;


public class Service {

	private final static String serviceURL = "http://localhost:3000/";

	private final static JsonParser jsonParser = new JsonParser();
	

	private Service() { }

	static JsonObject post(String uri, Map<String, String> params){
		JsonObject jsonObj = new JsonObject();
		try {
			String response = HttpClient.getInstance().post(serviceURL, uri, params);
			jsonObj = jsonParser.parse(response).getAsJsonObject();
		} catch (Exception e) {
			JsonObject error = jsonParser.parse(e.getMessage()).getAsJsonObject();
			jsonObj.addProperty("error", error.get("error").getAsString());
		} 
		return jsonObj;
	}

	static JsonObject get(String uri, Map<String, String> params){		
		JsonObject jsonObj = new JsonObject();
		try {
			String response = HttpClient.getInstance().get(serviceURL, uri, params);
			jsonObj = jsonParser.parse(response).getAsJsonObject();
		} catch (Exception e) {
			JsonObject error = jsonParser.parse(e.getMessage()).getAsJsonObject();
			jsonObj.addProperty("error", error.get("error").getAsString());
		} 
		return jsonObj;
	}
}
