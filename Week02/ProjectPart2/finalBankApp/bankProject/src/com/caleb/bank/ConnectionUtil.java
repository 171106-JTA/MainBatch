package com.caleb.bank;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException {
		
		
		String url = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:orcl"; 
		String username = "caleb"; 
		String password = "caleb1234";  
		
		return DriverManager.getConnection(url, username, password); 
	}
	
	
}

