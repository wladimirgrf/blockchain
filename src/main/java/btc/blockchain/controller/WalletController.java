package btc.blockchain.controller;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import btc.blockchain.info.core.Wallet;


@RestController
public class WalletController implements Serializable {

	private static final long serialVersionUID = -5769019243577790650L;

	@RequestMapping(value = "/create", produces = "application/json; charset=utf-8")
	public JsonObject create (Model model, @RequestParam String api_code, @RequestParam String password, String privateKey) {
		return Wallet.create(api_code, password, privateKey, null, null);
	}
	
	@RequestMapping(value = "/balance/{guid}", produces = "application/json; charset=utf-8")
	public JsonObject balance (Model model, @PathVariable("guid") String guid, @RequestParam String password, @RequestParam int key) {
		return Wallet.balance(guid, password, key);
	}
}
