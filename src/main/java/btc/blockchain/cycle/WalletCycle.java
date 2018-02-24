package btc.blockchain.cycle;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.model.Request;
import btc.blockchain.model.Status;
import btc.blockchain.rpc.controller.WalletController;

public class WalletCycle {

	@Autowired
	private WalletController controller;
	
	
	public void prepareToCreate(Request request) {
		JSONObject result = controller.create(request.getId());
		
		if(result.containsKey("error")) {
			request.setStatus(Status.ERROR);
		} else {
			request.setStatus(Status.COMPLETED);
		}
		
		request.setResult(result);
	}
	
	
	@SuppressWarnings("unchecked")
	public void prepareToGetBalance(Request request) {
		JSONObject properties = request.getProperties();
		List<String> errors = new ArrayList<String>();

		if(!properties.containsKey("address")) {
			errors.add("missing address");
		}
		
		JSONObject result = new JSONObject();

		if(errors.size() > 0) {
			result.put("error", errors);
		} else {
			String address 	= (String) properties.get("address");
			result = controller.balance(address);
		}

		if(result.containsKey("error")) {
			request.setStatus(Status.ERROR);
		} else {
			request.setStatus(Status.COMPLETED);
		}

		request.setResult(result);
	}
}
