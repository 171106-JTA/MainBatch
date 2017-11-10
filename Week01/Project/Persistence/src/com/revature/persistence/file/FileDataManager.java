package com.revature.persistence.file;

public final class FileDataManager extends FileDataUpdator {
	private static FileDataManager manager;
	
	private FileDataManager() {
	
	}
	
	public static FileDataManager getManager() {
		return manager == null ? manager = new FileDataManager() : manager;
	}
}
