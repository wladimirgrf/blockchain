package btc.blockchain.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;


public class RPCTransaction {
	
	public static JSONObject send (String id, String privateKey, String toAddress, long amount, Long fee) {
		Map<String, Long> vout = new HashMap<String, Long>();
		vout.put(toAddress, amount);

		return sendMany(id, privateKey, vout, fee);
	}
	
	public static JSONObject sendMany (String id, String privateKey, Map<String, Long> vout, Long fee) {
		List<String> params = new ArrayList<String>();
		params.add(id);
		params.add(vout.toString());
		
		return RPCClient.getInstance().rpcInvoke(RPCCalls.SEND_MANY.toString(), params);
	}
}
