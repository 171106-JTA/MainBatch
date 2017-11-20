package com.miniproject2.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties prop;
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException{
		try {
			prop = new Properties();
			prop.load(new FileInputStream(FILE_NAME));
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		return DriverManager.getConnection(url, username, password);
	}

}
