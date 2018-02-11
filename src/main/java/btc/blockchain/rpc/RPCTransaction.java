package btc.blockchain.rpc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.security.BIP38;


public class RPCTransaction implements Serializable {

	private static final long serialVersionUID = 1520564735287357940L;

	private static final long satoshiValue = 100000000;
	
	@Autowired
	private RPCAddress rpcAddress;
	
	@Autowired
	private RPCClient rpcClient;

	@SuppressWarnings("unchecked")
	public JSONObject send (Long id, String bip38Cipher, int bip38Key, String toAddress, long satoshiAmount) {
		List<String> params = new ArrayList<String>();
		params.add(id.toString());
		params.add(toAddress);
		params.add(satoshiToBtc(satoshiAmount));

		if(!rpcClient.testNode()) {
			JSONObject obj = new JSONObject();
			obj.put("error", "node off");
			return obj;
		}
		return rpcClient.rpcInvoke(RPCCalls.SEND_FROM.toString(), params);
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject validate (String bip38Cipher, int bip38Key, String toAddress, long satoshiAmount, long fee) {
		JSONObject obj = BIP38.decrypt(bip38Cipher, bip38Key);
		
		if(obj.containsKey("error")) {
			return obj;
		}
		
		obj.clear();
		
		if(!rpcClient.testNode()) {
			obj.put("error", "node off");
			return obj;
		}
		
		if(!rpcAddress.isValid(toAddress)) {
			obj.put("error", "address is not valid");			
		}
		return obj;
	}
	
	private String satoshiToBtc (long satoshiAmount) {
		BigDecimal coin = BigDecimal.valueOf(satoshiAmount);
		coin = coin.divide(new BigDecimal(satoshiValue));
		coin = coin.setScale(8, BigDecimal.ROUND_HALF_UP);
		return coin.toPlainString();
	}
}
