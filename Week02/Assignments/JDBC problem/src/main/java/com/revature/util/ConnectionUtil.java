package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;



public class ConnectionUtil {
	private static Properties prop; //Makes our property reference
	private final static String FILE_NAME = "DB.properties"; 
	
	public static Connection getConection() throws SQLException {
		try {
			prop = new Properties(); //instantiate our properties object
			prop.load(new FileInputStream(FILE_NAME)); //Use the filestream to populate properties
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
//		String url = prop.getProperty("url"); 
//		String username = prop.getProperty("username"); 
//		String password = prop.getProperty("password"); 
//		Class.forName(prop.getProperty("class")); //Optional but some may have to do this
//		return DriverManager.getConnection(url, username,password); 
		
		//safely populating properties object, using system environment variables
		String props[] = System.getenv("DB_Properties").split(";"); 
		return DriverManager.getConnection(props[1],props[2],props[3]); 
		
	}
	
	
}
