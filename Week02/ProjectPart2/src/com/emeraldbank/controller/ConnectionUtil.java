package com.emeraldbank.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionUtil {
	private Properties prop; 
	private Connection conn; 
	
	public static Connection getConnection()  throws SQLException {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String username = "EmeraldCityBankApp"; 
		String password = "passwordecba"; 		
		
		
		return DriverManager.getConnection(url, username, password);
	}

}
