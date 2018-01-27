package btc.blockchain.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class RPCWallet {
	
	
	public static JSONObject create(String id) {
		List<String> params = new ArrayList<String>();
		params.add(id);
		
		return RPCClient.getInstance().rpcInvoke(RPCCalls.GET_NEW_ADDRESS.toString(), params);
	}

	public static JSONObject dumpPrivateKey(String id, String cryptoKey) {
		return null;
	}
	
	public static JSONObject balance(String id, String cryptoKey) {
		return null;
	}
}
