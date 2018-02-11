package btc.blockchain.controller;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class WalletController implements Serializable {

	private static final long serialVersionUID = -5769019243577790650L;

	@RequestMapping(value = "/create")
	public JSONObject create (@RequestParam String api_code, String privateKey) {
		return null;
	}
	
	@RequestMapping(value = "/balance/{guid}")
	public JSONObject balance (@PathVariable("guid") String guid, @RequestParam String password, @RequestParam int key) {
		return null;
	}
}
