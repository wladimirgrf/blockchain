package btc.blockchain.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


public class EntityManagerFactoryService {

	private EntityManagerFactory emf;
	
	@PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }	
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}