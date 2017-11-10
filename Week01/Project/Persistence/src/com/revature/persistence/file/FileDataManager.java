package com.revature.persistence.file;

/**
 * @author Antony Lulciuc
 */
public final class FileDataManager extends FileDataUpdator {
	private static FileDataManager manager;
	
	private FileDataManager() {
		// do nothing
	}
	
	/**
	 * @return Instance of self
	 */
	public static FileDataManager getManager() {
		return manager == null ? manager = new FileDataManager() : manager;
	}
}
