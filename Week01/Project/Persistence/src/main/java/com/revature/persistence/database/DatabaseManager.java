package com.revature.persistence.database;

public final class DatabaseManager extends DatabaseUpdator {
	private static DatabaseManager manager = new DatabaseManager();
	
	private DatabaseManager() {
		// do nothing
	}
	
	public static DatabaseManager getManager() {
		return manager;
	}
}
