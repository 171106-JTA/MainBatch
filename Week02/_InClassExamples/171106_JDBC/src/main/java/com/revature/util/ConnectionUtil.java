package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:orcl";
		String username = "bobbert";
		String password = "p4ssw0rd";
		
		return DriverManager.getConnection(url,username, password);
	}
}
