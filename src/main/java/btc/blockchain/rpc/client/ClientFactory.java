package btc.blockchain.rpc.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import btc.blockchain.rpc.model.Method;


public class ClientFactory implements Serializable {

	private static final long serialVersionUID = -4335777621988743815L;
	
	private HttpPost httpPost;

	private Credentials credentials;
	

	public ClientFactory(Credentials credentials, HttpPost httpPost){
		this.credentials = credentials;
		this.httpPost = httpPost;
	}

	public JSONObject invoke(Method method, List<String> params) throws ParseException, ClientProtocolException, org.json.simple.parser.ParseException, IOException {	
		return httpResponseToJSON(exec(method, params));
	}

	@SuppressWarnings("unchecked")
	private HttpResponse exec(Method method, List<String> params) throws ClientProtocolException, IOException {
		JSONObject obj = new JSONObject();
		obj.put("method", method.toString());

		if (params != null) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			obj.put("params", params);
		}
		
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, credentials);
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		StringEntity myEntity = new StringEntity(obj.toJSONString());
		httpPost.setEntity(myEntity);
		
		return client.execute(httpPost);	
	}

	private JSONObject httpResponseToJSON(HttpResponse response) throws ParseException, org.json.simple.parser.ParseException, IOException  {
		if( response == null) {
			return null;
		}
		
		HttpEntity entity = response.getEntity();
		JSONParser parser = new JSONParser();

		return (JSONObject) parser.parse(EntityUtils.toString(entity));
	}
}
