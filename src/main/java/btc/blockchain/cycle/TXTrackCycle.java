package btc.blockchain.cycle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Request;
import btc.blockchain.model.Status;
import btc.blockchain.rpc.controller.TransactionController;

public class TXTrackCycle implements Runnable {
	
	@Autowired
	private RequestDAO dao;
	
	@Autowired
	private TransactionController controller;
	
	private Long requestId;
	
	private ScheduledExecutorService scheduleExecutorService;
	
	
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	
	public void setScheduleExecutorService(ScheduledExecutorService scheduleExecutorService) {
		this.scheduleExecutorService = scheduleExecutorService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		if(scheduleExecutorService == null || requestId == null || requestId <= 0) {
			return;
		}
		
		Request request = dao.get(requestId);
		
		if(request == null) {
			scheduleExecutorService.shutdown();
			return;
		}
		
		JSONObject result = request.getResult();
		List<String> errors = new ArrayList<String>();
		
		if(!result.containsKey("txHash")) {
			errors.add("missing txHash");
		}
		
		if(errors.size() > 0) {
			JSONObject txResult = new JSONObject();
			txResult.put("error", errors);
			result = txResult;
		} else {
			String txHash = (String) result.get("txHash");
			result = controller.getTx(txHash);
		}
		
		if(result.containsKey("error") && result.get("error") != null) {
			request.setStatus(Status.ERROR);
			
		} else if (result.containsKey("confirmations")){
			long confirmations = (long) result.get("confirmations");
			if(confirmations >= 6) {
				request.setStatus(Status.COMPLETED);
			}
		}
		
		request.setResult(result);
		dao.merge(request);
		
		if(request.getStatus() == Status.ERROR || request.getStatus() == Status.COMPLETED) {
			scheduleExecutorService.shutdown();
		}
	}
}
