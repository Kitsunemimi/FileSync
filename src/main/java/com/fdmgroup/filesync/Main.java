package com.fdmgroup.filesync;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.fdmgroup.filesync.dao.SyncEventDAO;
import com.fdmgroup.filesync.model.*;

/**
 * Main controller for the app server.
 * @author Harris.Fok
 *
 */
public class Main {
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	private static Logger appLogger = Logger.getLogger("appLogger");
	
	public static void main(String[] args) {
		String path1 = "C:/Users/Harris.Fok/JavaEclipseWorkspace/FileSyncSoloProject";
		String path2 = "C:/Users/Harris.Fok/Downloads";
		
		Synchronizer sync = new Synchronizer(path1, path2);
		
		SyncEventDAO seDao = SyncEventDAO.getInstance();
		seDao.close();
		
		
//		State s1 = new State(path1);
//		State s2 = new State(path2);
//		s1.calculate();
//		s2.calculate();
		
//		ArrayList<Change>[] derp = null;
//		try {
//			derp = Synchronizer.calculateChanges(s1, new State("C:/Users/Harris.Fok/JavaEclipseWorkspace/FileSyncSoloProject"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(derp[0]);
		
		
//		SyncEvent se = new SyncEvent(s1, s2);
//		
//		SyncEventDAO seDao = SyncEventDAO.getInstance();
//		
//		/*
//		FileInfo f = new FileInfo("H:/Opportunities.txt", null);
//		FileInfo f2 = new FileInfo("H:/Java/Builder Design.pptx, null");
//		sDao.addFileInfo(f);
//		sDao.addFileInfo(f2);
//		*/
//		
//		seDao.create(se);
//		
//		seDao.close();
	}
}
