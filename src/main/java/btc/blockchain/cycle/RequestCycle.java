package btc.blockchain.cycle;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Request;


public class RequestCycle  implements Runnable {
	
	@Autowired
	private TransactionCycle transactionCycle;
	
	@Autowired
	private WalletCycle walletCycle;

	@Autowired
	private RequestDAO dao;
	
	private ScheduledExecutorService scheduleExecutorService;
	
	public void setScheduleExecutorService(ScheduledExecutorService scheduleExecutorService) {
		this.scheduleExecutorService = scheduleExecutorService;
	}


	@Override
	public void run() {
		try {
			List<Request> requests = dao.getByStatus(Status.INLINE);

			if(requests == null) {
				System.out.println("No request");
				

				scheduleExecutorService.shutdown();
				
				return;
			}

			Request request = requests.get(0);
			request.setStatus(Status.LOCK);
			dao.merge(request);

			System.out.println(request.toString());

			switch (request.getMethod()) {
			case SEND_FROM:
				transactionCycle.prepareToSend(request);
				break;
			case GET_RECEIVED_BY_ADDRESS:
				walletCycle.prepareToGetBalance(request);
				break;
			case GET_NEW_ADDRESS:
				walletCycle.prepareToCreate(request);
				break;
			default:
				request.setStatus(Status.ERROR);
				break;
			}

			System.out.println(request.toString());
			dao.merge(request);
			
			

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
