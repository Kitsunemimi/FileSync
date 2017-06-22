package com.fdmgroup.filesync;

import java.util.ArrayList;
import java.util.TreeSet;

import com.fdmgroup.filesync.model.FileInfo;
import com.fdmgroup.filesync.model.State;

public final class Synchronizer {

	private Synchronizer() {
		super();
	}

	/**
	 * Given two States, calculates the differences between the two.
	 * Returns a set of lists of changes (add, move, del)
	 * 
	 * @param current
	 * @param old
	 * @return A set of 3 lists containing changes
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<FileInfo>[] calculateChanges(State current, State old) {
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
					sort3.add(sort1.pollFirst());
					sort4.add(sort2.pollFirst());
					// calculate checksum
				}
			} else if (sort1.first().getPath().compareTo(sort2.first().getPath()) < 0) {
				// A file was either moved/renamed, or newly added
				sort3.add(sort1.pollFirst());
				// calculate checksum
			} else {
				// A file was either moved/renamed, or deleted
				sort4.add(sort2.pollFirst());
			}
		}
		sort3.addAll(sort1);	// Add any remaining files from the original
		sort4.addAll(sort2);	// sets; depends on which emptied first
		
		// Prepare output lists
		ArrayList<FileInfo> add = new ArrayList<>();
		ArrayList<FileInfo> move = new ArrayList<>();
		ArrayList<FileInfo> del = new ArrayList<>();
		// Second pass (identify which files have been moved, modified, added
		// or deleted)
		while (!sort3.isEmpty() && !sort4.isEmpty()) {
			if (sort3.first().getChecksum() == sort4.first().getChecksum()) {
				// The file contents are the same and the files were simply
				// moved/renamed
				move.add(sort3.pollFirst());
				move.add(sort4.pollFirst());
			} else if (sort3.first().getChecksum() < sort4.first().getChecksum()) {
				// A new file was created
				add.add(sort3.pollFirst());
			} else {
				// A file has been deleted
				del.add(sort4.pollFirst());
			}
		}
		add.addAll(sort3);
		del.addAll(sort4);
		
		return new ArrayList[] {add, move, del};
	}
	
	public static void filterChanges() {
		/*
		 * Given two sets of lists of changes, filters out the changes that are the
		 * same across the two.
		 * Returns the filtered sets.
		 */
		return;
	}
}
