package com.revature.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {
	public static Connection getConnection() throws SQLException{

		/*
		 * Get connection details from system environment variable
		 */
		String prop[] = System.getenv("DBProps").split(";");
		return DriverManager.getConnection(prop[0], prop[1], prop[2]);
			
	}
}
