package com.fdmgroup.filesync;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.fdmgroup.filesync.dao.SyncEventDAO;
import com.fdmgroup.filesync.model.*;
import static com.fdmgroup.filesync.model.Change.Type.*;

public class Synchronizer {
	private static Logger appLogger = Logger.getLogger("appLogger");
	
	private State s1;
	private State s2;
	private State oldS1;
	private State oldS2;

	public Synchronizer(String path1, String path2) {
		appLogger.info("Initializing Synchronizer...");
		appLogger.debug("Paths to sync:\n\t" + path1 + "\n\t" + path2);
		
		s1 = new State(path1);
		s1.calculate();
		s2 = new State(path2);
		s2.calculate();
		
		// Fetch the previous sync states for both paths
		SyncEventDAO seDao = SyncEventDAO.getInstance();
		
		SyncEvent se = seDao.read(s1.getPath(), s2.getPath());
		if (se != null) {
			appLogger.info("Previous sync event found.");
			oldS1 = se.getS1();
			oldS2 = se.getS2();
		} else {
			appLogger.info("Previous sync event not found. First-time sync "
					+ "will be performed");
			oldS1 = new State(s1.getPath());
			oldS2 = new State(s2.getPath());
		}
	}
	
	public void getChanges() throws IOException {
		HashSet<Change>[] changes1 = calculate(s1, oldS1);
		HashSet<Change>[] changes2 = calculate(s2, oldS2);

		filter(changes1, changes2);
		
	}
	
	/**
	 * Given two States, calculates the differences between the two.
	 * Returns a set of lists of changes (add, move, del)
	 * 
	 * @param current
	 * @param old
	 * @return A set of 3 lists containing changes
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public HashSet<Change>[] calculate(State current, State old) throws IOException {
		appLogger.debug("Calculating changes applied on " + current.getPath());
		
		// First pass sets (all files)
		TreeSet<FileInfo> sort1 = new TreeSet<>(new FileInfo.PathComparator());
		TreeSet<FileInfo> sort2 = new TreeSet<>(new FileInfo.PathComparator());
		// Second pass sets (added/moved/modified/deleted files)
		TreeSet<FileInfo> sort3 = new TreeSet<>(new FileInfo.ChecksumComparator());
		TreeSet<FileInfo> sort4 = new TreeSet<>(new FileInfo.ChecksumComparator());

		sort1.addAll(current.getFiles());
		sort2.addAll(old.getFiles());
		
		// First pass (find and eliminate files that haven't been changed)
		while (!sort1.isEmpty() && !sort2.isEmpty()) {
			if (sort1.first().getPath() == sort2.first().getPath()) {
				if (sort1.first().getModifiedTime() == sort2.first().getModifiedTime()) {
					// Both files match and can merely be popped off both lists
					// Simultaneously copy the checksum from the old state
					sort1.pollFirst().copyChecksum(sort2.pollFirst());
					// TODO: test this checksum
				} else {
					// The files have the same name but have either been
					// moved/renamed, or modified
					FileInfo f1 = sort1.pollFirst();
					f1.calculateChecksum();
					
					sort3.add(f1);
					sort4.add(sort2.pollFirst());
				}
				
			} else if (sort1.first().getPath().compareTo(sort2.first().getPath()) < 0) {
				// A file was either moved/renamed, or newly added
				FileInfo f = sort1.pollFirst();
				f.calculateChecksum();
				
				sort3.add(f);
				
			} else {
				// A file was either moved/renamed, or deleted
				sort4.add(sort2.pollFirst());
			}
		}
		// Add any remaining files from the original sets, depending on which
		// emptied first
		for (FileInfo f : sort1) {
			f.calculateChecksum();
			sort3.add(f);
		}
		sort4.addAll(sort2);
		
		// Prepare second pass and output lists
		HashSet<Change> add = new HashSet<>();
		HashSet<Change> move = new HashSet<>();
		HashSet<Change> del = new HashSet<>();
		
		// TODO: extra mechanism for handling empty files (checksum 0)
		
		Path currPath = Paths.get(current.getPath());
		Path oldPath = Paths.get(old.getPath());
		
		// Second pass (identify which files have been moved, added, or deleted)
		while (!sort3.isEmpty() && !sort4.isEmpty()) {
			if (sort3.first().getChecksum() == sort4.first().getChecksum()) {
				// The file contents are the same and the files were simply
				// moved/renamed
				move.add(newChange(MOVE, sort4.pollFirst(), sort3.pollFirst(), currPath, oldPath));
				
			} else if (sort3.first().getChecksum() < sort4.first().getChecksum()) {
				// A new file was created
				add.add(newChange(ADD, null, sort3.pollFirst(), currPath, oldPath));
				
			} else {
				// A file has been deleted
				del.add(newChange(DEL, sort4.pollFirst(), null, currPath, oldPath));
			}
		}
		while (!sort3.isEmpty())
			add.add(newChange(ADD, null, sort3.pollFirst(), currPath, oldPath));
		while (!sort4.isEmpty())
			del.add(newChange(DEL, sort4.pollFirst(), null, currPath, oldPath));
		
		return new HashSet[] {add, move, del};
	}
	
	/**
	 * Helper function for making a new Change
	 * 
	 * @param type
	 * @param f1
	 * @param f2
	 * @param currPath
	 * @param oldPath
	 * @return
	 */
	private Change newChange(Change.Type type, FileInfo f1, FileInfo f2,
			Path currPath, Path oldPath) {
		String before = null;
		String after = null;
		
		if (f1 != null)
			before = oldPath.relativize(Paths.get(f1.getPath())).toString();
		if (f2 != null)
			after = currPath.relativize(Paths.get(f2.getPath())).toString();
		
		return new Change(type, before, after);
	}

	/**
	 * Given two sets of lists of changes, filters out the changes that are the
	 * same between the two.
	 * 
	 * @param changes1
	 * @param changes2
	 */
	public void filter(HashSet<Change>[] changes1, HashSet<Change>[] changes2) {
		for (int i = 0; i < 3; i++) {
			for (Iterator<Change> it = changes1[i].iterator(); it.hasNext(); ) {
				Change c = it.next();
				if (changes2[i].contains(c)) {
					it.remove();
					changes2[i].remove(c);
				}
			}
		}
	}
}
