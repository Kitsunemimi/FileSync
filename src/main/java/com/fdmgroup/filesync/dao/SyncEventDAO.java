package com.fdmgroup.filesync.dao;

import javax.persistence.EntityManager;

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

	public void close() {
		dao.close();
		seDao = null;
	}
}
