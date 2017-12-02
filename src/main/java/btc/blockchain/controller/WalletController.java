package btc.blockchain.controller;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import btc.blockchain.info.core.Wallet;


@RestController
public class WalletController implements Serializable {

	private static final long serialVersionUID = -5769019243577790650L;

	@RequestMapping(value = "/create")
	public JsonObject create (@RequestParam String api_code, String privateKey) {
		try {
			return Wallet.create(api_code, privateKey);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	@RequestMapping(value = "/balance/{guid}")
	public JsonObject balance (@PathVariable("guid") String guid, @RequestParam String password, @RequestParam int key) {
		return Wallet.balance(guid, password, key);
	}
}
