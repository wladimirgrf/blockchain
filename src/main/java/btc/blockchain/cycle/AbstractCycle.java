package btc.blockchain.cycle;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCycle implements Runnable{

	private ScheduledFuture<?> scheduleFuture;

	@Override
	public void run() {
		if(!keepRunning()) {
			System.out.println("Interrupt");
			scheduleFuture.cancel(true);
			return;
		}
		task();
	}

	abstract boolean keepRunning();
	
	abstract void task();


}
