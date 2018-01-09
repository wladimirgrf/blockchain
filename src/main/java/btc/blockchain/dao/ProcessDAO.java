package btc.blockchain.dao;

import java.util.List;
import javax.persistence.Query;

import btc.blockchain.model.Process;


public class ProcessDAO extends AbstractDAO<Process> {

	private static final long serialVersionUID = -3658484244017967121L;

	@SuppressWarnings("unchecked")
	public Process getByTxHash(String txHash) {
		Query query = entityManager.createQuery("from Process where txHash = :txHash").setParameter("txHash", txHash);
		List<Process> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Process> getByFromAddress(String address) {
		Query query = entityManager.createQuery("from Process where address = :address").setParameter("address", address);
		List<Process> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Process> getByStatus(int status) {
		Query query = entityManager.createQuery("from Process where status = :status").setParameter("status", status);
		List<Process> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	protected Class<Process> getServiceClass() {
		return Process.class;
	}
}