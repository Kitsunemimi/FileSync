package com.fdmgroup.filesync;

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
	
	
}
