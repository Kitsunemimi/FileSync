package net.kitsunemimi.filesync.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import net.kitsunemimi.filesync.model.State;
import net.kitsunemimi.filesync.model.SyncEvent;

public class SyncEventDAO {
	
	private static SyncEventDAO dao;
	private PersistenceManager pm;
	
	private SyncEventDAO() {
		pm = PersistenceManager.getInstance();
	}
	
	public static SyncEventDAO getInstance() {
		if (dao == null)
			dao = new SyncEventDAO();
		
		return dao;
	}
	
	public static boolean instanceExists() {
		return dao != null;
	}
	
	/**
	 * Add a SyncEvent and its States to the database
	 * @param se The SyncEvent to persist in the database
	 */
	public void create(SyncEvent se) {
		StateDAO sDao = StateDAO.getInstance();
		sDao.create(se.getS1());
		sDao.create(se.getS2());
		
		EntityManager em = pm.getEntityManager();
		em.getTransaction().begin();
		em.persist(se);
		em.getTransaction().commit();
	}
	
	public SyncEvent read(int id) {
		EntityManager em = pm.getEntityManager();
		return em.find(SyncEvent.class, id);
	}
	
	public SyncEvent read(String path1, String path2) {
		EntityManager em = pm.getEntityManager();
		TypedQuery<SyncEvent> query = em.createNamedQuery("SyncEvent.findByPaths", SyncEvent.class);
		query.setParameter("path1", path1).setParameter("path2", path2);
		
		// TODO: Test that this returns the latest result
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public SyncEvent read(State s1, State s2) {
		EntityManager em = pm.getEntityManager();
		TypedQuery<SyncEvent> query = em.createNamedQuery("SyncEvent.findByStates", SyncEvent.class);
		query.setParameter("s1", s1).setParameter("s2", s2);
		
		// TODO: Test that this returns the latest result
		return query.getSingleResult();
	}
	
	public List<SyncEvent> readAll() {
		EntityManager em = pm.getEntityManager();
		TypedQuery<SyncEvent> query = em.createQuery("SELECT se from SyncEvent se", SyncEvent.class);
		return query.getResultList();
	}

	public void close() {
		pm.close();
		dao = null;
	}
}
