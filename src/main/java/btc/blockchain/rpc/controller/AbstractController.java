package btc.blockchain.rpc.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.ParseException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.bitcoin.core.AddressFormatException;

import btc.blockchain.rpc.client.ClientFactory;
import btc.blockchain.rpc.model.Method;
import btc.blockchain.security.BIP38;


public class AbstractController implements Serializable {

	private static final long serialVersionUID = -8243576889643154071L;
	
	@Autowired
	private ClientFactory clientFactory;
	
	
	@SuppressWarnings("unchecked")
	protected JSONObject invoke(Method method, String... args) {
		List<String> params = Arrays.asList(args);
		JSONObject object = new JSONObject();
		try {
			object = clientFactory.invoke(method, params);
		} catch (ParseException | org.json.simple.parser.ParseException | IOException e) {
			object.put("error", e.getMessage());
		}
		
		return object;
	}
	
	@SuppressWarnings("unchecked")
	protected JSONObject importPrivateKey(Long id, String bip38Cipher, int bip38Key) {
		JSONObject importedWallet = new JSONObject();
		try {
			String privateKey = (String) BIP38.decrypt(bip38Cipher, bip38Key).get("private_key");
			importedWallet = invoke(Method.IMPORT_PRIV_KEY, privateKey, id.toString());;
		} catch (UnsupportedEncodingException | AddressFormatException | GeneralSecurityException e) {
			importedWallet.put("error", e.getMessage());
		}
		
		return importedWallet;
	}
	
	public boolean isValidAddress(String address) {			
		JSONObject validatedAddress = invoke(Method.VALIDATE_ADDRESS, address);
		if(validatedAddress.containsKey("error") && validatedAddress.get("error") != null) {
			return false;
		}
		JSONObject result = (JSONObject) validatedAddress.get("result");
		if(result.get("isvalid").toString() == "false") {
			return false;
		}
		
		return true;
	}
}
