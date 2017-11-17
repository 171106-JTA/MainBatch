package com.revature.factory;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	private static Properties propFile;
	private final static String FILE_NAME="db.properties"; 
	
	public static Connection getConnection() throws SQLException {
		try {
			propFile = new Properties();
			propFile.load(new FileInputStream(FILE_NAME));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// with files 
		//String username = propFile.getProperty("username");
		//String password = propFile.getProperty("password");
			
		String props[] = System.getenv("DBPropsTemp").split(";");
		
		
		return DriverManager.getConnection(props[1],props[2],props[3]);
	}
}
