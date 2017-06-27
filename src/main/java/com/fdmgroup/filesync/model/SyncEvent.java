package com.fdmgroup.filesync.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * This class is used to identify and track sync events between all directory
 * pairs. Necessary for finding old states when calculating new changes.
 * 
 * @author Harris.Fok
 */
@Entity
@Table(name = "SYNC_EVENT")
@NamedQueries({
	@NamedQuery(name = "SyncEvent.findByPaths",
				query = "SELECT se FROM SyncEvent se "
					+ "WHERE (se.path1 = :path1 AND se.path2 = :path2) "
					+ "   OR (se.path1 = :path2 AND se.path2 = :path1) "
					+ "ORDER BY se.date DESC"),
	@NamedQuery(name = "SyncEvent.findByStates",
				query = "SELECT se FROM SyncEvent se "
					+ "WHERE (se.s1 = :s1 AND se.s2 = :s2) "
					+ "   OR (se.s1 = :s2 AND se.s2 = :s1) "
					+ "ORDER BY se.date DESC")
})
public class SyncEvent {
	
	@Id
	@Column(name = "SYNC_EVENT_ID")
	@SequenceGenerator(name = "seSeq", sequenceName = "SYNC_EVENT_SEQ",
					   allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seSeq")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID_1")
	private State s1;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID_2")
	private State s2;
	
	@Column(name = "PATH_1")
	private String path1;

	@Column(name = "PATH_2")
	private String path2;
	
	@Column(name = "SYNC_TIME")
	private long date;
	
	
	// Constructor
	public SyncEvent() {
		super();
	}
	
	public SyncEvent(State s1, State s2) {
		this.s1 = s1;
		this.s2 = s2;
		path1 = s1.getPath();
		path2 = s2.getPath();
		date = new Date().getTime();
	}
	

	// Getters/setters
	public State getS1() {
		return s1;
	}

	public void setS1(State s1) {
		this.s1 = s1;
	}

	public State getS2() {
		return s2;
	}

	public void setS2(State s2) {
		this.s2 = s2;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
}
