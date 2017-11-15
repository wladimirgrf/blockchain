package btc.blockchain.controller;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import btc.blockchain.info.core.Transaction;


@RestController
public class TransactionController implements Serializable {

	private static final long serialVersionUID = 8155431350750583974L;

	@RequestMapping(value = "/send/{guid}")
	public JsonObject send (Model model, @PathVariable("guid") String guid, @RequestParam String password, @RequestParam String toAddress, @RequestParam long amount, @RequestParam String fromAddress, @RequestParam Long fee, String note) {
		return Transaction.send(guid, password, toAddress, amount, fromAddress, fee, note);
	}
}
