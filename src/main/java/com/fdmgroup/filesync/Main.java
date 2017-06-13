package com.fdmgroup.filesync;

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
		State s2 = new State("H:/derp");
		
		s1.calculate();
	}
}
