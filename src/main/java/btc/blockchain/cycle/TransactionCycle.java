package btc.blockchain.cycle;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Request;
import btc.blockchain.rpc.controller.TransactionController;


public class TransactionCycle  implements Runnable {
	
	@Autowired
	private RequestDAO dao;

	@Autowired
	private TransactionController rpc;


	@Override
	public void run() {
		List<Request> transactions = dao.getByStatus(Status.INLINE);

		if(transactions == null) {
			System.out.println("No transaction");
			return;
		}
		
		Request transaction = transactions.get(0);
		
		transaction.setStatus(Status.LOCK);
		dao.merge(transaction);
		
		System.out.println(transaction);
		
		//JSONObject jsonObj = rpc.send(transaction.getId(), transaction.getBip38Cipher(), transaction.getBip38Key(), transaction.getToAddress(), transaction.getSatoshiAmount());
	}
	
	
	

}
