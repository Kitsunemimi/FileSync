package com.fdmgroup.filesync;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Contains information about a particular directory and its files.
 * @author Harris.Fok
 *
 */
public class State {
	private static Logger rootLogger = Logger.getRootLogger();
	
	private String path;
	private ArrayList<FileInfo> files;
	
	// Constructor
	public State(String path) {
		// TODO: check if path is valid
		this.path = path;
	}
	
	// Getters/setters
	public String getPath() {
		return path;
	}

	public ArrayList<FileInfo> getFiles() {
		return files;
	}
	
	// Functions
	// Calculates the state of all files in the directory specified by this
	// object's path.
	public void calculate() {
		rootLogger.debug("Calculating state for '" + path + "'.");
		files = new ArrayList<>();
		recurseDirectory(new File(path));
	}
	
	// Recursively get all FileInfos for a directory.
	// TODO: This code is currently vulnerable to stack overflow.
	private void recurseDirectory(File dir) {
		File[] fileList = dir.listFiles();
		
		for (File f : fileList) {
			if (f.isDirectory()) {
				rootLogger.trace("Directory: " + f.getName());
				recurseDirectory(f);
			} else {
				rootLogger.trace("File: " + f.getName());
				FileInfo fi = new FileInfo(f.getAbsolutePath());
				files.add(fi);
			}
		}
	}
}
