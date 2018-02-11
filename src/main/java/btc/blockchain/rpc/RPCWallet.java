package btc.blockchain.rpc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.security.BIP38;

public class RPCWallet implements Serializable {

	private static final long serialVersionUID = -8243576889643154071L;
	
	@Autowired
	private RPCClient rpcClient;
	
	public JSONObject dumpPrivateKey(Long id, String bip38Cipher, int bip38Key) {
		JSONObject obj = importPrivateKey(id, bip38Cipher, bip38Key);
		
		if(obj.containsKey("error")) {
			return obj;
		}
		
		List<String> params = new ArrayList<String>();		
		params.add(id.toString());
		
		return rpcClient.rpcInvoke(RPCCalls.DUMP_PRIVATE_KEY.toString(), params);
	}
	
	
	public JSONObject importPrivateKey(Long id, String bip38Cipher, int bip38Key) {
		List<String> params = new ArrayList<String>();
		JSONObject jsonObj = BIP38.decrypt(bip38Cipher, bip38Key);
		
		if(jsonObj.containsKey("error")) {
			return jsonObj;
		}
		
		params.add((String) jsonObj.get("private_key"));
		params.add(id.toString());
		
		return rpcClient.rpcInvoke(RPCCalls.IMPORT_PRIV_KEY.toString(), params);
	}
}
