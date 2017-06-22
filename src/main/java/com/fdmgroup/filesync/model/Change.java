package com.fdmgroup.filesync.model;

/**
 * Change class that represents a change that has occurred on a file system.
 * Used as "instructions" to perform when executing synchronization.
 * @author Harris.Fok
 *
 */
public class Change {
	public enum Type {
		ADD, MOVE, DEL
	};

	private Type type;
	private String originalPath;
	private String newPath;
	
	// Constructor
	public Change(Type type, String originalPath, String newPath) {
		super();
		this.type = type;
		this.originalPath = originalPath;
		this.newPath = newPath;
	}

	// Getters/setters
	public Type getType() {
		return type;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public String getNewPath() {
		return newPath;
	}

	// Functions
	@Override
	public String toString() {
		return "Change [type=" + type + ", originalPath=" + originalPath + ", "
				+ "newPath=" + newPath + "]\n";
	}
}
