package btc.blockchain.cycle;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import btc.blockchain.model.Transaction;
import btc.blockchain.model.Status;

@Service
public class CycleFactory {
	
	private final static int THREAD_POOL_LIMIT = 5;
	
	@Autowired
	private TransactionCycle transactionCycle;
	
	
	@PostConstruct
	public void start() {
		ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(THREAD_POOL_LIMIT);
		scheduleExecutorService.scheduleAtFixedRate(transactionCycle, 0, 1, TimeUnit.MINUTES);
	}
	

	
	
	private Transaction getT() {
		Transaction t = new Transaction();
		t.setId(1l);
		t.setSatoshiAmount(1000000);
		t.setFee(500);
		t.setToAddress("msNhm676VaSTiUXM8moEih34RbrM2AKSXZ");
		t.setBip38Cipher("6PYWpAUkWHf65BP7xEv3qzus7D2xpU2f4SaYWtwbN1tXG6Ce5hi2FmN5V4");
		t.setBip38Key(3);
		t.setStatus(Status.INLINE);
		
		return t;
	}
}

