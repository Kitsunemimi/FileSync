package com.fdmgroup.filesync;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StateFileInfoTest {
	
	private State s1;
	private State s2;
	
	@BeforeClass
	public static void setup() {
		// create a test directory structure
		// include hidden, readonly, and set a specific modified date
	}
	
	@AfterClass
	public static void tearDown() {
		// remove the test structure
	}
	
	@Before
	public void before() {
		s1 = new State("C:/Users/Harris.Fok/Downloads/test");
		s2 = new State("H:/test");
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void generalStateTest() {
		
	}
	
	@Test
	public void subdirectoriesStateTest() {
		
	}
	
	@Test
	public void modifiedDateTest() {
		
	}
	
	@Test
	public void hiddenTest() {
		
	}
	
	@Test
	public void readOnlyTest() {
		
	}
	
	@Test
	public void invalidStatePath() {
		
	}
	
	@Test
	public void invalidFileInfoPath() {
		
	}
}
