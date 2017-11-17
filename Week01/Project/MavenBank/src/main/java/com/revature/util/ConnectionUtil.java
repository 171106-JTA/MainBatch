package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		String username = "ew_1"; 
		String password = "RevRev5";
		
		return DriverManager.getConnection(url, username, password);
	}
}
