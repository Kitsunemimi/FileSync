package com.fdmgroup.filesync;

import java.io.File;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Contains information about a single file.
 * @author Harris.Fok
 *
 */
@Entity
@Table(name = "FILE_INFO")
public class FileInfo {
	
	@Id
	@Column
	@SequenceGenerator(name = "fiSeq", sequenceName = "FILE_INFO_SEQ", allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fiSeq")
	private int id;
	
	@Column(nullable = false, length = 260)
	private String path;
	
	@Column(name = "modified_time")
	private long modifiedTime;
	
	@Column
	private long checksum;

	@Type(type = "yes_no")
	@Column(name = "is_hidden")
	private boolean isHidden;
	
	@Type(type = "yes_no")
	@Column(name = "is_read_only")
	private boolean isReadOnly;
	
	// TODO: read/write/execute perms
	
	@ManyToOne(fetch = FetchType.LAZY)
	private State state;
	
	
	// Comparators
	public static class PathComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fi1, FileInfo fi2) {
			return fi1.getPath().compareTo(fi2.getPath());
		}
	}
	
	public static class ChecksumComparator implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo fi1, FileInfo fi2) {
			return ((Long)fi1.getChecksum()).compareTo(fi2.getChecksum());
		}
	}

	
	// Constructors
	public FileInfo() {
		super();
	}

	public FileInfo(String path, State state) {
		File f = new File(path);
		if (!f.isFile()) {
			throw new IllegalArgumentException("Cannot create file info, "
					+ "specified path does not point to a valid file.");
		}
		
		this.path = path;
		this.modifiedTime = f.lastModified();
		this.isHidden = f.isHidden();
		this.isReadOnly = false;		// TODO: canRead, canWrite, canExecute
		this.state = state;
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

	
	// Functions
	
	// TODO: Function for calculating checksum (they are not calculated on creation)
	
	/**
	 * Copy the checksum from another FileInfo.
	 * This function exists as you cannot manually set checksums with a setter.
	 * 
	 * @param otherFile The other file to copy the checksum off of
	 */
	public void copyChecksum(FileInfo otherFile) {
		this.checksum = otherFile.getChecksum();
	}
	
	@Override
	public String toString() {
		return "FileInfo [path=" + path + ", modified=" + modifiedTime + ", checksum=" + checksum + "]";
	}
	
	
}
