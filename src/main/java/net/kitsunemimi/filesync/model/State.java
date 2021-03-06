package net.kitsunemimi.filesync.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * Contains FileInfos for a specified directory on a particular device.
 * @author Harris.Fok
 *
 */
@Entity
@Table(name = "STATE")
public class State {
	private static Logger appLogger = Logger.getLogger("appLogger");

	@Id
	@Column(name = "STATE_ID")
	@SequenceGenerator(name = "sSeq", sequenceName = "STATE_SEQ",
					   allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sSeq")
	private int id;

	@Column(nullable = false, length = 260)
	private String path;
	
	@Column(name = "DEVICE_ID")
	private int deviceID;
	
	@OneToMany(mappedBy = "state")
	private List<FileInfo> files = new ArrayList<>();
	
	// Constructors
	public State() {
		super();
	}
	
	public State(String path) {
		// TODO: include device id in constructor
		if (!(new File(path).isDirectory())) {
			// TODO: create new directory?
			throw new IllegalArgumentException("Cannot create State, specified"
					+ " path is not a valid directory");
		}
		
		this.path = new File(path).getAbsolutePath();
	}

	// Getters/setters
	public String getPath() {
		return path;
	}

	public List<FileInfo> getFiles() {
		return files;
	}
	
	// Functions
	// Calculates the state of all files in the directory specified by this
	// object's path.
	public void calculate() {
		appLogger.debug("Calculating state for '" + path + "'.");
		recurseDirectory(new File(path));
		appLogger.debug("State calculated succesfully. Contains "+ files.size()
																+ " files.");
	}
	
	// Recursively get all FileInfos for a directory.
	// TODO: This code is currently vulnerable to stack overflow.
	private void recurseDirectory(File dir) {
		File[] fileList = dir.listFiles();
		
		for (File f : fileList) {
			if (f.isDirectory()) {
				appLogger.trace("Directory: " + f.getName());
				recurseDirectory(f);
			} else {
				appLogger.trace("File: " + f.getName());
				FileInfo fi = new FileInfo(f.getAbsolutePath(), this);
				files.add(fi);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("State [path=" + path + ",\n"
				 					+ "       deviceID=" + deviceID + ",\n"
				 					+ "       files=\n");
		for (FileInfo file : files) {
			output.append(file + "\n");
		}
		return output.append("]").toString();
	}
}
