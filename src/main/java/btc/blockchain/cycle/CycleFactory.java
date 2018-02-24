package btc.blockchain.cycle;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CycleFactory {
	
	private final static int THREAD_POOL_LIMIT = 5;
	
	@Autowired
	private RequestCycle requestCycle;
	
	
	@PostConstruct
	public void start() {
		ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(THREAD_POOL_LIMIT);
		scheduleExecutorService.scheduleAtFixedRate(requestCycle, 0, 1, TimeUnit.MINUTES);
	}
}

