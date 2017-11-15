package btc.blockchain.info.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Transaction {

	private Transaction() { }

	public static JsonObject send (String guid, String password, String toAddress, long amount, String fromAddress, Long fee, String note) {
		Map<String, Long> recipient = new HashMap<String, Long>();
		recipient.put(toAddress, amount);

		return sendMany(guid, password, recipient, fromAddress, fee, note);
	}

	public static JsonObject sendMany (String guid, String password, Map<String, Long> recipients, String fromAddress, Long fee, String note) {
		Map<String, String> params = new HashMap<String, String>();

		params.put("password", password);

		String method = null;

		if (recipients.size() == 1) {
			method = "payment";
			Entry<String, Long> e = recipients.entrySet().iterator().next();
			params.put("to", e.getKey());
			params.put("amount", e.getValue().toString());
		} else {
			method = "sendmany";
			params.put("recipients", new Gson().toJson(recipients));
		}

		if (fromAddress != null) {
			params.put("from", fromAddress);
		}
		if (fee != null) {
			params.put("fee", fee.toString());
		}
		if (note != null) {
			params.put("note", note);
		}        

		String uri = String.format("merchant/%s/%s", guid, method);
		return Service.post(uri, params);
	}
}


