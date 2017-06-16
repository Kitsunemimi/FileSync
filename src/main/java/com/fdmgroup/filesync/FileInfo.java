package com.fdmgroup.filesync;

import java.io.File;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private State state;
	
	// Constructors
	public FileInfo() {
		super();
	}

	public FileInfo(String path, State state) {
		File f = new File(path);
		if (!f.isFile()) {
			// TODO: throw an error
		}
		
		this.path = path;
		this.modifiedTime = f.lastModified();
		this.checksum = 0;		// TODO: calculate checksum
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

	@Override
	public String toString() {
		return "FileInfo [path=" + path + ", modified=" + modifiedTime + ", checksum=" + checksum + "]";
	}
	
	
}
