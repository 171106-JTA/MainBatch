package com.revature.trms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException {
		String props[] = System.getenv("DBProps").split(";");

		return DriverManager.getConnection(props[1], props[2], props[3]);
	}
}
