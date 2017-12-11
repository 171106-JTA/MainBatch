package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	private static Properties prop;
	private final static String FILE_NAME= "DB.properties";
	public static Connection getConnection() throws SQLException{
		StringTokenizer st= null;
		String props[]= System.getenv("DBProps").split(";");//create environment variable dbprops and then use it to fill variables
		return DriverManager.getConnection(props[1],props[2],props[3]);
		
	}
}