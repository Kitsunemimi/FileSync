package net.kitsunemimi.filesync.dao;

import javax.persistence.EntityManager;

import net.kitsunemimi.filesync.model.FileInfo;

public class FileInfoDAO {
	
	private static FileInfoDAO dao;
	private PersistenceManager pm;
	
	private FileInfoDAO() {
		pm = PersistenceManager.getInstance();
	}
	
	public static FileInfoDAO getInstance() {
		if (dao == null)
			dao = new FileInfoDAO();
		
		return dao;
	}
	
	public static boolean instanceExists() {
		return dao != null;
	}
	
	/**
	 * Add a FileInfo to the database
	 * @param f The FileInfo to persist in the database
	 */
	public void create(FileInfo f) {
		EntityManager em = pm.getEntityManager();
		em.getTransaction().begin();
		em.persist(f);
		em.getTransaction().commit();
	}

	public void close() {
		pm.close();
		dao = null;
	}
}
