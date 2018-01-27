package btc.blockchain.cycle;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.core.Wallet;
import btc.blockchain.dao.TransactionDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Transaction;


public class TransactionCycle implements Runnable {
	
	@Autowired
	private static TransactionDAO transactionDAO;
	
	private BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);

	@Override
	public void run() {
		List<Transaction> transactions = transactionDAO.getByStatus(Status.INLINE.ordinal());
		
		
		
	}


	

}
