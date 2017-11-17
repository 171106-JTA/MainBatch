package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
//	private static Properties prop;  //Make or property reference
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException {
//		try {
//			prop = new Properties(); //Instantiate our property object
//			prop.load(new FileInputStream(FILE_NAME)); //User a filestream to populate properties
//		} catch(Exception e) {
//			e.printStackTrace(); 
//		}
		
//		String url = prop.getProperty("url");
//		String username = prop.getProperty("username"); 
//		String password = prop.getProperty("password");
		
		String prop[] = System.getenv("DBProps").split(";"); 
		System.out.println(prop);
		return DriverManager.getConnection(prop[1], prop[2], prop[3]);
	}
}
