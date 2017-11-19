package com.bankoftheapes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
	
		//safest way to populate properties
		String props[] = System.getenv("DBProp").split(";");
		
		return DriverManager.getConnection(props[0], props[1], props[2]);
	}
}
