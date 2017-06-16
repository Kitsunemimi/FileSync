package com.fdmgroup.filesync;

import java.util.List;

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
	
	/**
	 * Add a state and all files contained to the database
	 * @param s The state to persist in the database
	 */
	public void addState(State s) {
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		
		List<FileInfo> files = s.getFiles();
		for (FileInfo f : files) {
			em.persist(f);
		}
		em.persist(s);

		em.getTransaction().commit();
	}
	
	// Temp
	public void addFileInfo(FileInfo f) {
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}
}
