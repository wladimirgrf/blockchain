package btc.blockchain.cycle;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;



import btc.blockchain.info.core.Wallet;


public class ProcedureCycle implements Callable<Integer>{
	
	private String guid;
	
	private String password;
	
	private int key;
	
	private BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);


	@Override
	public Integer call() throws Exception {
		Random random = new Random();
        int duration = random.nextInt(4000);
        
        if(duration > 5000) {
            throw new IOException("Sleeping for too long.");
        }
        
        System.out.println("Starting ...");
        
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Finished.");
        
        return duration;
	}

}
