package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private ConnectionUtil() {}
	
	public static Connection getConnection() {
		final String props[] = System.getenv("TRMSDB").split(";");
		
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection(props[1], props[2], props[3]);
		} catch(SQLException e) {
			System.out.println("error getting connection");
			e.printStackTrace();
		}

		return conn;
	}
}