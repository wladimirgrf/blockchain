package btc.blockchain.dao;

import java.util.List;
import javax.persistence.Query;

import btc.blockchain.model.Procedure;


public class ProcedureDAO extends AbstractDAO<Procedure> {

	private static final long serialVersionUID = -3658484244017967121L;

	@SuppressWarnings("unchecked")
	public Procedure getByTxHash(String txHash) {
		Query query = entityManager.createQuery("from Process where txHash = :txHash").setParameter("txHash", txHash);
		List<Procedure> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Procedure> getByFromAddress(String address) {
		Query query = entityManager.createQuery("from Process where address = :address").setParameter("address", address);
		List<Procedure> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Procedure> getByStatus(int status) {
		Query query = entityManager.createQuery("from Process where status = :status").setParameter("status", status);
		List<Procedure> result = query.getResultList();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	protected Class<Procedure> getServiceClass() {
		return Procedure.class;
	}
}