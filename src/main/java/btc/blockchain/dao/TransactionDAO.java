package btc.blockchain.dao;

import java.util.List;
import javax.persistence.Query;

import btc.blockchain.model.Status;
import btc.blockchain.model.Transaction;


public class TransactionDAO extends AbstractDAO<Transaction> {

	private static final long serialVersionUID = -3658484244017967121L;

	@SuppressWarnings("unchecked")
	public Transaction getByTxHash(String txHash) {
		Query query = entityManager.createQuery("from Transaction where txHash = :txHash").setParameter("txHash", txHash);
		List<Transaction> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByStatus(Status status) {
		Query query = entityManager.createQuery("from Transaction where status = :status").setParameter("status", status);
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