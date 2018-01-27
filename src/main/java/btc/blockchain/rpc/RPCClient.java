package btc.blockchain.rpc;

import java.io.Serializable;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
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

import org.springframework.beans.factory.annotation.Autowired;


public class RPCClient implements Serializable {

	private static final long serialVersionUID = -8517760270335249137L;

	private static RPCClient instance;

	private static boolean nodeOn;

	@Autowired
	private HttpPost httpPost;

	@Autowired
	private Credentials credentials;

	private RPCClient() { 
		testNode();
	}


	public static synchronized RPCClient getInstance() {
		if (instance == null) {
			instance = new RPCClient();
		}
		return instance;
	}

	public JSONObject rpcInvoke(String method, List<String> params) {
		if(!nodeOn) {
			return null;
		}
		
		JSONObject jsonObj = null;

		HttpResponse response = rpcExec(method, params);
		if(response != null) {
			jsonObj = httpResponseToJSON(response);
		}

		return jsonObj;
	}

	public synchronized boolean testNode() {
		HttpResponse response = rpcExec(RPCCalls.GET_NETWORK_INFO.toString(), null);
		nodeOn = (response != null && response.getStatusLine().getStatusCode() == 200 ? true : false);
		return  nodeOn;
	}
	
	public boolean getNodeOn() {
		return nodeOn;
	}

	@SuppressWarnings("unchecked")
	private HttpResponse rpcExec(String method, List<String> params) {
		JSONObject json = new JSONObject();
		json.put("method", method);

		if (params != null) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}

		System.out.println(credentials.getPassword());
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, credentials);
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		try {
			StringEntity myEntity = new StringEntity(json.toJSONString());
			httpPost.setEntity(myEntity);
			return client.execute(httpPost);	
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	private JSONObject httpResponseToJSON(HttpResponse response)  {
		if( response == null) {
			return null;
		}
		HttpEntity entity = response.getEntity();
		JSONParser parser = new JSONParser();
		
		try {
			return (JSONObject) parser.parse(EntityUtils.toString(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
