package btc.blockchain.cycle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.dao.ProcessDAO;
import btc.blockchain.model.Process;
import btc.blockchain.model.Status;


public class ExecutorCycle {
	
	private final static int THREAD_POOL_LIMIT = 5;
	
	@Autowired
	private static ProcessDAO procedureDAO;

	public static void main(String[] args) {
		
		List<Process> procedures = procedureDAO.getByStatus(Status.INLINE.ordinal());

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_LIMIT);

		List<Future<Integer>> list = new ArrayList<Future<Integer>>();

		for(int i=0; i < 4; i++) {
			Future<Integer> future = executor.submit(new ProcedureCycle());

			list.add(future);
		}
		executor.shutdown();

		for(@SuppressWarnings("rawtypes") Future f : list) {
			try {

				System.out.println("Result is: " + f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				IOException ex = (IOException) e.getCause();

				System.out.println(ex.getMessage());
			}
		}
	}
	
}

