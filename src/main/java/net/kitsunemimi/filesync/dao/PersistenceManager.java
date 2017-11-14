package net.kitsunemimi.filesync.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton persistence manager that handles entity managers for data access
 */
public class PersistenceManager {
	
	private static PersistenceManager pm;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	// Private constructor
	private PersistenceManager() {
		init();
	}
	
	private void init() {
		emf = Persistence.createEntityManagerFactory("FileSync");
		em = emf.createEntityManager();
	}
	
	public static PersistenceManager getInstance() {
		if (pm == null)
			pm = new PersistenceManager();
		
		return pm;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public void close() {
		if (pm != null) {
			em.close();
			emf.close();
			pm = null;
		}
	}
}
