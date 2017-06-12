package com.fdmgroup.filesync;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Data access object that implements singleton design pattern with JPA.
 * @author Harris.Fok
 *
 */
public class DataAccessObject {
	
	private static DataAccessObject dao;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	// Private constructor
	private DataAccessObject() {
		init();
	}
	
	private void init() {
		emf = Persistence.createEntityManagerFactory("FileSyncSoloProject");
		em = emf.createEntityManager();
	}
	
	public static DataAccessObject getInstance() {
		if (dao == null) {
			dao = new DataAccessObject();
		}
		
		return dao;
	}
	
	public EntityManager getEntityManager(){
		return em;
	}
}
