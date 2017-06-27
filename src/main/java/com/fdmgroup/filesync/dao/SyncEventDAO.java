package com.fdmgroup.filesync.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fdmgroup.filesync.model.State;
import com.fdmgroup.filesync.model.SyncEvent;

public class SyncEventDAO {
	
	private static SyncEventDAO seDao;
	private DataAccessObject dao;
	
	private SyncEventDAO() {
		dao = DataAccessObject.getInstance();
	}
	
	public static SyncEventDAO getInstance() {
		if (seDao == null) {
			seDao = new SyncEventDAO();
		}
		
		return seDao;
	}
	
	/**
	 * Add a SyncEvent and its States to the database
	 * @param f The SyncEvent to persist in the database
	 */
	public void create(SyncEvent se) {
		StateDAO sDao = StateDAO.getInstance();
		sDao.create(se.getS1());
		sDao.create(se.getS2());
		
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		em.persist(se);
		em.getTransaction().commit();
	}
	
	public SyncEvent read(int id) {
		EntityManager em = dao.getEntityManager();
		return em.find(SyncEvent.class, id);
	}
	
	public SyncEvent read(String path1, String path2) {
		EntityManager em = dao.getEntityManager();
		TypedQuery<SyncEvent> query = em.createNamedQuery("SyncEvent.findByPaths", SyncEvent.class);
		query.setParameter("path1", path1).setParameter("path2", path2);
		
		// TODO: Test that this returns the latest result
		return query.getSingleResult();
	}
	
	public SyncEvent read(State s1, State s2) {
		EntityManager em = dao.getEntityManager();
		TypedQuery<SyncEvent> query = em.createNamedQuery("SyncEvent.findByStates", SyncEvent.class);
		query.setParameter("s1", s1).setParameter("s2", s2);
		
		// TODO: Test that this returns the latest result
		return query.getSingleResult();
	}
	
	public List<SyncEvent> readAll() {
		EntityManager em = dao.getEntityManager();
		TypedQuery<SyncEvent> query = em.createQuery("SELECT se from SyncEvent se", SyncEvent.class);
		return query.getResultList();
	}

	public void close() {
		dao.close();
		seDao = null;
	}
}
