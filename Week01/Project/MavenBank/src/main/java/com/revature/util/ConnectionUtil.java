package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {		
	public static Connection getConnection() throws SQLException {
		
		String prop[] = System.getenv("DBProps").split(";"); 
		return DriverManager.getConnection(prop[1], prop[2], prop[3]);
	}
}
