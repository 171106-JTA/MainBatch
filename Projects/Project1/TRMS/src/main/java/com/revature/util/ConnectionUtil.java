package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Utility class which creates a connection with the database using environment variables as authentication
 */
public class ConnectionUtil {
	/**
	 * Create connection using environment variables as authentication
	 * @return Connection - connection to database
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		String props[]= System.getenv("TRMS").split(";");
		return DriverManager.getConnection(props[1],props[2],props[3]);
	}
}
