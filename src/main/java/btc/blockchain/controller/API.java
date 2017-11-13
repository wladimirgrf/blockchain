package btc.blockchain.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import btc.blockchain.info.core.Wallet;

@RestController
public class API implements Serializable{

	private static final long serialVersionUID = -5769019243577790650L;

	@RequestMapping(value = "/address/{address}")
	public JsonObject getAddress(Model model, @PathVariable("address") String address) {
		return Wallet.getAddress(address);
	}
}
