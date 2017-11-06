package net.kitsunemimi.filesync.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.zip.CRC32;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

/**
 * Contains information about a single file.
 * 
 * @author Harris.Fok
 */
@Entity
@Table(name = "FILE_INFO")
public class FileInfo {
	private static Logger appLogger = Logger.getLogger("appLogger");
	
	@Id
	@Column(name = "FILE_INFO_ID")
	@SequenceGenerator(name = "fiSeq", sequenceName = "FILE_INFO_SEQ",
					   allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fiSeq")
	private int id;
	
	@Column(nullable = false, length = 260)
	private String path;
	
	@Column(name = "MODIFIED_TIME")
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
	@JoinColumn(name = "STATE_ID")
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
	/**
	 * Calculates the checksum for the file specified in this FileInfo.
	 * 
	 * @throws IOException 
	 */
	public void calculateChecksum() throws IOException {
		InputStream input = new BufferedInputStream(new FileInputStream(path));
		CRC32 crc = new CRC32();
		int b = input.read();
		
		while (b != -1) {
			crc.update(b);
			b = input.read();
		}
		
		input.close();
		checksum = crc.getValue();
		appLogger.trace("Calculated checksum: " + checksum);
	}
	
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
		return "FileInfo [path=" + path + ", modified=" + modifiedTime + ", "
				+ "checksum=" + checksum + "]";
	}
	
	
}
