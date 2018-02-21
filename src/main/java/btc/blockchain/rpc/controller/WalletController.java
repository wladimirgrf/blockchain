package btc.blockchain.rpc.controller;

import org.json.simple.JSONObject;

import btc.blockchain.rpc.model.Method;


public class WalletController extends AbstractController {

	private static final long serialVersionUID = -8243576889643154071L;


	public JSONObject create(Long id) {	
		return invoke(Method.GET_NEW_ADDRESS, id.toString());
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject balance(String address) {
		JSONObject object = new JSONObject();
		
		if(!isValidAddress(address)) {
			object.put("error", "address is not valid");	
			return object;
		}
		return invoke(Method.GET_RECEIVED_BY_ADDRESS, address);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject dumpPrivateKey(Long id, String bip38Cipher, int bip38Key) {
		JSONObject importedWallet = new JSONObject();
		
		if(!isValidCipher(bip38Cipher, bip38Key)) {
			importedWallet.put("error", "cipher or key incorrect");			
		}
		
		importedWallet = importPrivateKey(id, bip38Cipher, bip38Key);
		
		if(importedWallet.containsKey("error")) {
			return importedWallet;
		}
		return invoke(Method.DUMP_PRIVATE_KEY, id.toString());
	}
}
