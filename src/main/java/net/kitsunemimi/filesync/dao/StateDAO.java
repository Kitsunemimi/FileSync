package net.kitsunemimi.filesync.dao;

import java.util.List;

import javax.persistence.EntityManager;

import net.kitsunemimi.filesync.model.FileInfo;
import net.kitsunemimi.filesync.model.State;

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
		
		EntityManager em = dao.getEntityManager();
		em.getTransaction().begin();
		
		List<FileInfo> files = s.getFiles();
		for (FileInfo f : files) {
			em.persist(f);
		}
		em.persist(s);
		
		em.getTransaction().commit();
	}
	
	public void close() {
		dao.close();
		sDao = null;
	}
}
