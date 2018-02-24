package btc.blockchain.cycle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Request;
import btc.blockchain.rpc.model.Method;


public class RequestCycle  implements Runnable {
	
	@Autowired
	private TransactionCycle transactionCycle;
	
	@Autowired
	private WalletCycle walletCycle;

	@Autowired
	private RequestDAO dao;


	@Override
	public void run() {
		try {
			List<Request> requests = dao.getByStatus(Status.INLINE);

			if(requests == null) {
				System.out.println("No request");
				return;
			}

			Request request = requests.get(0);
			request.setStatus(Status.LOCK);
			dao.merge(request);

			System.out.println(request.toString());

			if(request.getMethod() == Method.SEND_FROM) {
				transactionCycle.prepareToSend(request);
			} else if(request.getMethod() == Method.GET_RECEIVED_BY_ADDRESS) {
				walletCycle.prepareToGetBalance(request);
			} else if(request.getMethod() == Method.GET_NEW_ADDRESS) {
				walletCycle.prepareToCreate(request);
			} else {
				request.setStatus(Status.ERROR);
			}

			dao.merge(request);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}




}
