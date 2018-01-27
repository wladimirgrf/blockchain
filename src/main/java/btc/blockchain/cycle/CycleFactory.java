package btc.blockchain.cycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.dao.TransactionDAO;
import btc.blockchain.model.Transaction;
import btc.blockchain.model.Status;


public class CycleFactory {
	
	private final static int THREAD_POOL_LIMIT = 5;
	
	private ScheduledFuture<?> scheduleFuture;
	
	


	
	public void scheduleThread(AbstractCycle cycle, int minutes) {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		scheduleFuture = executor.scheduleAtFixedRate(cycle, 0, minutes, TimeUnit.MINUTES);
	}
	
	public static void main (String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		        System.out.println("Thread - "+ new Date().getTime());
		    }
		}, 0, 30, TimeUnit.SECONDS);
	}
	
}

