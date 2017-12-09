package com.trms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "trms_app";
		String password = "password_trms"; 
		

		try {
			Class.forName ("oracle.jdbc.OracleDriver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	
	
	
}
