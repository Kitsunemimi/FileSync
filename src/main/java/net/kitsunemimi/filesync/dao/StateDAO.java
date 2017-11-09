package net.kitsunemimi.filesync.dao;

import java.util.List;

import javax.persistence.EntityManager;

import net.kitsunemimi.filesync.model.FileInfo;
import net.kitsunemimi.filesync.model.State;

public class StateDAO {
	
	private static StateDAO dao;
	private PersistenceManager pm;
	
	private StateDAO() {
		pm = PersistenceManager.getInstance();
	}
	
	public static StateDAO getInstance() {
		if (dao == null)
			dao = new StateDAO();
		
		return dao;
	}
	
	/**
	 * Add a state and all files contained to the database
	 * @param s The state to persist in the database
	 */
	public void create(State s) {
//		FileInfoDAO fiDao = FileInfoDAO.getInstance();
//		List<FileInfo> files = s.getFiles();
//		for (FileInfo f : files) {
//			fiDao.create(f);
//		}
		
		EntityManager em = pm.getEntityManager();
		em.getTransaction().begin();
		
		List<FileInfo> files = s.getFiles();
		for (FileInfo f : files) {
			em.persist(f);
		}
		em.persist(s);
		
		em.getTransaction().commit();
	}
	
	public void close() {
		pm.close();
		dao = null;
	}
}
