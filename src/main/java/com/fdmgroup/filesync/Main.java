package com.fdmgroup.filesync;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Main controller for the app server.
 * @author Harris.Fok
 *
 */
public class Main {
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	private static Logger rootLogger = Logger.getRootLogger();
	
	public static void main(String[] args) {
		State s1 = new State("C:/Users/Harris.Fok/JavaEclipseWorkspace/FileSyncSoloProject");
		//State s2 = new State("H:/derp");
		
		s1.calculate();
		
		ArrayList<FileInfo>[] derp = Synchronizer.calculateChanges(s1, new State());
		
		System.out.println(derp[0]);
		
		StateDAO sDao = StateDAO.getInstance();
		
		/*
		FileInfo f = new FileInfo("H:/Opportunities.txt", null);
		FileInfo f2 = new FileInfo("H:/Java/Builder Design.pptx, null");
		sDao.addFileInfo(f);
		sDao.addFileInfo(f2);
		*/
		
		//sDao.addState(s1);
		
		sDao.close();
	}
}
