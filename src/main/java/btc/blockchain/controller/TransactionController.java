package btc.blockchain.controller;

import java.io.Serializable;

import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btc.blockchain.dao.TransactionDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Transaction;
import btc.blockchain.rpc.RPCTransaction;


@RestController
public class TransactionController implements Serializable {

	private static final long serialVersionUID = 8155431350750583974L;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private RPCTransaction rpc;
	
	
	@RequestMapping(value = "/send")
	public String send (@RequestParam String bip38Cipher, @RequestParam int bip38Key, @RequestParam String toAddress, @RequestParam long satoshiAmount, @RequestParam long fee) {
		JSONObject obj = rpc.validate(bip38Cipher, bip38Key, toAddress, satoshiAmount, fee);
	
		if(obj.containsKey("error")) {
			return obj.toJSONString();
		}
		return persist(bip38Cipher, bip38Key, toAddress, satoshiAmount, fee).toString();
	}
	
	private Transaction persist(String bip38Cipher, int bip38Key, String toAddress, long satoshiAmount, long fee) {
		Transaction entity = new Transaction();
		entity.setBip38Cipher(bip38Cipher);
		entity.setBip38Key(bip38Key);
		entity.setToAddress(toAddress);
		entity.setSatoshiAmount(satoshiAmount);
		entity.setFee(fee);
		entity.setStatus(Status.INLINE);
		
		transactionDAO.merge(entity);
		
		return entity;
	}
}
