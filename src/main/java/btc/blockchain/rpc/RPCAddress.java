package btc.blockchain.rpc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


public class RPCAddress implements Serializable {

	private static final long serialVersionUID = -8243576889643154071L;
	
	@Autowired
	private RPCClient rpcClient;
	
	public JSONObject create(Long id) {
		List<String> params = new ArrayList<String>();
		params.add(id.toString());
		
		return rpcClient.rpcInvoke(RPCCalls.GET_NEW_ADDRESS.toString(), params);
	}

	public static JSONObject dumpPrivateKey(String id, String bip38Cipher) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject balance(String address) {
		List<String> params = new ArrayList<String>();
		params.add(address);
		
		if(!rpcClient.testNode()) {
			JSONObject obj = new JSONObject();
			obj.put("error", "node off");
			return obj;
		}
		return rpcClient.rpcInvoke(RPCCalls.GET_RECEIVED_BY_ADDRESS.toString(), params);
	}
	
	public boolean isValid(String address) {
		List<String> params = new ArrayList<String>();
		params.add(address);
			
		if(!rpcClient.testNode()) {
			return false;
		}
		JSONObject obj = rpcClient.rpcInvoke(RPCCalls.VALIDATE_ADDRESS.toString(), params);
		JSONObject isValid = (JSONObject) obj.get("result");

		if(isValid.get("isvalid").toString() == "false") {
			return false;
		}
		return true;
	}
}
