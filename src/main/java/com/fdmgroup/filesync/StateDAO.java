package com.fdmgroup.filesync;

import javax.persistence.EntityManager;

public class StateDAO {
	
	private static StateDAO sDao;
	private DataAccessObject dao;
	
	private StateDAO() {
		dao = DataAccessObject.getInstance();
	}
	
	public static StateDAO getInstance() {
		if (sDao == null) {
			sDao = new StateDAO();
		}
		
		return sDao;
	}
	
	public void close() {
		dao.close();
		sDao = null;
	}
	
	// Temp
	public void addFileInfo(FileInfo f) {
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
}
