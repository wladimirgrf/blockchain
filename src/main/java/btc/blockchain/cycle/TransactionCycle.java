package btc.blockchain.cycle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.model.Request;
import btc.blockchain.model.Status;
import btc.blockchain.rpc.controller.TransactionController;

public class TransactionCycle implements Serializable {

	private static final long serialVersionUID = -909046715339523860L;
	
	@Autowired
	private TransactionController controller;

	
	@SuppressWarnings("unchecked")
	public void prepareToSend(Request request) {
		JSONObject properties = request.getProperties();
		List<String> errors = new ArrayList<String>();

		if(!properties.containsKey("toAddress")) {
			errors.add("missing toAddress");
		}
		if(!properties.containsKey("bip38Cipher")) {
			errors.add("missing bip38Cipher");
		}
		if(!properties.containsKey("bip38Key")) {
			errors.add("missing bip38Key");
		}
		if(!properties.containsKey("satoshiAmount")) {
			errors.add("missing satoshiAmount");
		}

		JSONObject result = new JSONObject();

		if(errors.size() > 0) {
			result.put("error", errors);
		} else {
			String bip38Cipher 	= (String) properties.get("bip38Cipher");
			String toAddress 	= (String) properties.get("toAddress");
			int bip38Key 		= (int) properties.get("bip38Key");
			long satoshiAmount 	= (long) properties.get("satoshiAmount");

			result = controller.send(request.getId(), bip38Cipher, bip38Key, toAddress, satoshiAmount);
		}

		if(result.containsKey("error") && result.get("error") != null) {
			request.setStatus(Status.ERROR);
		} else if(result.containsKey("result")){
			request.setStatus(Status.WAITING);
		} else {
			request.setStatus(Status.COMPLETED);
		}

		request.setResult(result);
	}
}
