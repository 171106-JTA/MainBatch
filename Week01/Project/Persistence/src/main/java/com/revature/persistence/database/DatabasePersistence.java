package com.revature.persistence.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.core.FieldParams;
import com.revature.persistence.Persistenceable;

public abstract class DatabasePersistence implements Persistenceable {
	
	// Connection Parameters
	public static final String DRIVERNAME = "drivername";
	public static final String SERVERNAME = "servername";
	public static final String DATABASE = "database";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String URL = "url";
	
	// Logger
	protected Logger logger = Logger.getLogger(DatabasePersistence.class);
	
	// Connection data 
	private static String driverName;
	private static String serverName;
	private static String database;
	private static String username;
	private static String password;
	private static String url;
	private static Connection connection;
	private static boolean initialized;
	
	public void setup(Object data) {
		// attempt to initialize connection 
		if (!initialized) {
			if (data instanceof FieldParams) {
				FieldParams params = (FieldParams)data;
				
				for (String key : params.keySet()) {
					switch (key) {
						case DRIVERNAME:
							driverName = params.get(key);
							break;
						case SERVERNAME:
							serverName = params.get(key);
							break;
						case DATABASE:
							database = params.get(key);
							break;
						case USERNAME:
							username = params.get(key);
							break;
						case PASSWORD:
							password = params.get(key);
							break;
						case URL:
							url = params.get(key);
							break;
					}
				}
				
				// Attempt connection
				initialized = connect();
			}
		}
	}	
	
	
	
	
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Attempt connection to database with current credentials 
	 * @return true if connection made else false
	 */
	private boolean connect() {
		try {
			logger.debug("attempting connection...");
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
			logger.debug("connection successful");
			return connection != null;
		} catch (ClassNotFoundException | SQLException e) {
			logger.debug("connection failed, message=" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	
	
}
