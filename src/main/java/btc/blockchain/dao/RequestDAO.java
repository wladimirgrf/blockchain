package btc.blockchain.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.Query;

import btc.blockchain.model.Status;
import btc.blockchain.model.Request;
import btc.blockchain.security.SHA256;


public class RequestDAO extends AbstractDAO<Request> {

	private static final long serialVersionUID = -3658484244017967121L;
	
	public void persist(Request entity) {
		if (entity != null) {
			try {
				entity.setTxId(SHA256.random());
				entityManager.merge(entity);
			} catch (NoSuchAlgorithmException e) { }
		}
	}

	@SuppressWarnings("unchecked")
	public Request getByTxId(String txId) {
		Query query = entityManager.createQuery("from Request where txId = :txId").setParameter("txId", txId);
		List<Request> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Request> getByStatus(Status status) {
		Query query = entityManager.createQuery("from Request where status = :status").setParameter("status", status);
		List<Request> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	protected Class<Request> getServiceClass() {
		return Request.class;
	}
}