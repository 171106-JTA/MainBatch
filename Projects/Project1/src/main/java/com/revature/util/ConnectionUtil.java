package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private ConnectionUtil() {}
	public static Connection getConnection() throws SQLException {
		final String props[] = System.getenv("TRMSDB").split(";");
		return DriverManager.getConnection(props[1], props[2], props[3]);
	}
}