package com.fdmgroup.filesync.dao;

import javax.persistence.EntityManager;

import com.fdmgroup.filesync.model.FileInfo;

public class FileInfoDAO {
	
	private static FileInfoDAO fiDao;
	private DataAccessObject dao;
	
	private FileInfoDAO() {
		dao = DataAccessObject.getInstance();
	}
	
	public static FileInfoDAO getInstance() {
		if (fiDao == null) {
			fiDao = new FileInfoDAO();
		}
		
		return fiDao;
	}
	
	/**
	 * Add a FileInfo to the database
	 * @param f The FileInfo to persist in the database
	 */
	public void create(FileInfo f) {
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}

	public void close() {
		dao.close();
		fiDao = null;
	}
}
