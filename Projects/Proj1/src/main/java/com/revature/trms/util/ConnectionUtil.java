package com.revature.trms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "polkaman", "polkaman");
	}
}
