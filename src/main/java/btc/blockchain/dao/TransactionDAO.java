package btc.blockchain.dao;

import java.util.List;
import javax.persistence.Query;

import btc.blockchain.model.Transaction;


public class TransactionDAO extends AbstractDAO<Transaction> {

	private static final long serialVersionUID = -3658484244017967121L;

	@SuppressWarnings("unchecked")
	public Transaction getByTxHash(String txHash) {
		Query query = entityManager.createQuery("from Process where txHash = :txHash").setParameter("txHash", txHash);
		List<Transaction> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByFromAddress(String address) {
		Query query = entityManager.createQuery("from Process where address = :address").setParameter("address", address);
		List<Transaction> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByStatus(int status) {
		Query query = entityManager.createQuery("from Process where status = :status").setParameter("status", status);
		List<Transaction> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	protected Class<Transaction> getServiceClass() {
		return Transaction.class;
	}
}