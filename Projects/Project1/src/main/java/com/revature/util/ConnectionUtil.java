package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private ConnectionUtil() {}
	public static Connection getConnection() throws SQLException {
		final String props[] = System.getenv("TRMSDB").split(";");
		Connection conn = DriverManager.getConnection(props[1], props[2], props[3]);
		
		if (conn == null) {
			System.out.println("error getting connection");
		} else {
			System.out.println("connection set up");
		}
		return conn;
	}
}