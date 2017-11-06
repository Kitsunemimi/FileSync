package net.kitsunemimi.filesync.model;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPath == null) ? 0 : newPath.hashCode());
		result = prime * result + ((originalPath == null) ? 0 : originalPath.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Change other = (Change) obj;
		if (newPath == null) {
			if (other.newPath != null)
				return false;
		} else if (!newPath.equals(other.newPath))
			return false;
		if (originalPath == null) {
			if (other.originalPath != null)
				return false;
		} else if (!originalPath.equals(other.originalPath))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Change [type=" + type + ", originalPath=" + originalPath + ", "
				+ "newPath=" + newPath + "]\n";
	}
}
