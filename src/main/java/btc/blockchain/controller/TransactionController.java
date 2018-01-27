package btc.blockchain.controller;

import java.io.Serializable;
import java.util.Collection;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import btc.blockchain.core.TransactionCore;
import btc.blockchain.dao.TransactionDAO;
import btc.blockchain.model.Transaction;


@RestController
public class TransactionController implements Serializable {

	private static final long serialVersionUID = 8155431350750583974L;
	
	@Autowired
	private TransactionDAO procedureDAO;

	@RequestMapping(value = "/send")
	public JSONObject send (@RequestParam String password, @RequestParam String toAddress, @RequestParam String fromAddress, @RequestParam long satoshiAmount, @RequestParam Long fee) {
		Collection<Transaction> procedures = procedureDAO.list();
		return null;
	}
}
