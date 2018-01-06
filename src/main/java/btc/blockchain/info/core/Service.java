package btc.blockchain.info.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class Service {

	private UsernamePasswordCredentials passwordCredentials;

	private HttpPost httpPost;
	
	public void setPasswordCredentials(UsernamePasswordCredentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}
	
	public void setHttpPost(HttpPost httpPost) {
		this.httpPost = httpPost;
	}

	private Service() { }
	
	public static void main(String[] args) {
//		String[] params = { "test1" };
//		JSONObject json = jsonRPCExec("getbalance", Arrays.asList(params));
//		System.out.println(json.get("result"));
	}
	
	@PostConstruct
	public void test() {
		System.out.println("----------------------------------------");
		System.out.println("pass cre - "+passwordCredentials);
		System.out.println("user - "+passwordCredentials.getUserName());
		System.out.println("----------------------------------------");
	}

	@SuppressWarnings("unchecked")
	static JSONObject jsonRPCExec(String method, List<String> params){
		JSONObject json = new JSONObject();
		json.put("method", method);
		if (null != params) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}
		JSONObject responseJsonObj = null;

		try {
			
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("test45", "test45");
			provider.setCredentials(AuthScope.ANY, credentials);
			CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
			
			
			HttpPost httppost = new HttpPost("http://localhost:8332");
			StringEntity myEntity = new StringEntity(json.toJSONString());
			httppost.setEntity(myEntity);
			HttpResponse response = client.execute(httppost);

			System.out.println(json.toString());


			System.out.println("executing request" + httppost.getRequestLine());

			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
			}
			JSONParser parser = new JSONParser();
			responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(entity));
			
			client.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return responseJsonObj;
	}

}
