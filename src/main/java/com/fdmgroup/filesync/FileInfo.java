package com.fdmgroup.filesync;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
	
	public FileInfo() {
		super();
	}

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
