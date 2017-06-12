package com.fdmgroup.filesync;

import java.io.File;

public class FileInfo {
	
	private String path;
	private long modifiedTime;
	private long checksum;
	private boolean isHidden;
	private boolean isReadOnly;
	
	public FileInfo(String path) {
		File f = new File(path);
		if (!f.isFile()) {
			// TODO: throw an error
		}
		
		this.path = path;
		this.modifiedTime = f.lastModified();
		this.checksum = 0;		// TODO: calculate checksum
		this.isHidden = f.isHidden();
		this.isReadOnly = false;		// TODO: canRead, canWrite, canExecute
	}

	// Getters/setters
	public String getPath() {
		return path;
	}

	public long getModifiedTime() {
		return modifiedTime;
	}

	public long getChecksum() {
		return checksum;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}
}
