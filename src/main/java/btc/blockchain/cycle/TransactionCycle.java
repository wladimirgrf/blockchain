package btc.blockchain.cycle;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import btc.blockchain.dao.TransactionDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Transaction;
import btc.blockchain.rpc.RPCTransaction;


public class TransactionCycle  implements Runnable {
	
	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private RPCTransaction rpc;


	@Override
	public void run() {
		List<Transaction> transactions = null;
		try {
			transactions = transactionDAO.getByStatus(Status.INLINE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(transactions == null) {
			System.out.println("No transaction");
			return;
		}
		
		Transaction transaction = transactions.get(0);
		
		transaction.setStatus(Status.LOCK);
		transactionDAO.merge(transaction);
		
		System.out.println(transaction);
		
		//JSONObject jsonObj = rpcTransaction.send(transaction.getId(), transaction.getBip38Cipher(), transaction.getBip38Key(), transaction.getToAddress(), transaction.getSatoshiAmount());
	}
	
	
	

}
