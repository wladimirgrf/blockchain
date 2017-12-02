package btc.blockchain.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class AbstractDAO<T> implements Serializable {

	private static final long serialVersionUID = 2588371344406743194L;

	public abstract Class<T> getServiceClass();

	protected EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public T get(Long id) {
		return id != null ? entityManager.find(getServiceClass(), id) : null;
	}

	public void merge(T entity) {
		if (entity != null) {
			entityManager.merge(entity);
		}
	}

	public void delete(T entity) {
		if (entity != null) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

	public Collection<T> list() {
		return list(0, 0);
	}

	public Collection<T> list(int page, int pageSize) {
		return list(page, pageSize, null, null);
	}	

	@SuppressWarnings("unchecked")
	public Collection<T> list(int page, int pageSize, String orderBy, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append(String.format("from %s o", getServiceClass().getSimpleName()));
		sql.append("order by ");
		if (orderBy != null && !orderBy.isEmpty() && order != null && !order.isEmpty()) {
			sql.append(String.format("o.%s %s", orderBy, order));	
		} else {
			sql.append("a.id desc");
		}
		Query query = entityManager.createQuery(sql.toString());
		if (pageSize > 0) {
			query.setMaxResults(pageSize);	
		}
		if (pageSize > 0 && page > 0) {
			query.setFirstResult((page - 1) * pageSize);
		}        
		return query.getResultList();
	}

	public long count() {
		Query query = entityManager.createQuery(String .format("select count(a) from %s o" , getServiceClass().getSimpleName()));
		return (Long)query.getSingleResult();
	}	
}