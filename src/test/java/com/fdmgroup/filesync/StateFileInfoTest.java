package com.fdmgroup.filesync;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.filesync.model.FileInfo;
import com.fdmgroup.filesync.model.State;

public class StateFileInfoTest {
	
	private State s1;
	
	@BeforeClass
	public static void setup() {
		// Create a test directory structure
		String base = "/Users/Harris.Fok/Downloads/test/";
		
		try {
			// Create test files
			File f1 = new File(base + "foo.txt");
			File f2 = new File(base + "bar/baz.txt");
			
			f1.getParentFile().mkdirs();
			f2.getParentFile().mkdirs();
			f1.createNewFile();
			f2.createNewFile();
			
			// Set attributes for testing
			Files.setAttribute(f2.toPath(), "dos:hidden", true);
			f2.setLastModified(1337);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		// Remove the test structure of the original directory
		String base = "/Users/Harris.Fok/Downloads/test/";
		File f1 = new File(base + "foo.txt");
		File f2 = new File(base + "bar/baz.txt");
		File f3 = f2.getParentFile();
		
		f1.delete();
		f2.delete();
		f3.delete();
	}
	
	@Before
	public void before() {
		s1 = new State("/Users/Harris.Fok/Downloads/test/");
		s1.calculate();
	}
	
	@After
	public void after() {
		
	}
	
	@Test
	public void generalStateTest() {
		List<FileInfo> files = s1.getFiles();
		
		FileInfo f1 = files.get(0);
		FileInfo f2 = files.get(1);

		assertEquals("C:\\Users\\Harris.Fok\\Downloads\\test\\bar\\baz.txt", f1.getPath());
		assertEquals("C:\\Users\\Harris.Fok\\Downloads\\test\\foo.txt", f2.getPath());
	}
	
	@Test
	public void checksumTest() {
		
	}
	
	@Test
	public void modifiedDateTest() {
		List<FileInfo> files = s1.getFiles();
		
		FileInfo f1 = files.get(0);
		FileInfo f2 = files.get(1);

		assertEquals(1337, f1.getModifiedTime());
		assertNotEquals(1337, f2.getModifiedTime());
	}
	
	@Test
	public void hiddenTest() {
		List<FileInfo> files = s1.getFiles();
		
		FileInfo f1 = files.get(0);
		FileInfo f2 = files.get(1);

		assertEquals(true, f1.isHidden());
		assertEquals(false, f2.isHidden());
	}
	
	@Test
	public void readOnlyTest() {
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidStatePath() {
		@SuppressWarnings("unused")
		State s2 = new State("/Users/Harris.Fok/Downloads/tessst/");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidFileInfoPath() {
		@SuppressWarnings("unused")
		FileInfo f4 = new FileInfo("/Users/Harris.Fok/Downloads/test/derp.txt", null);
	}
}
