package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static final String FILE_NAME = "DB.properties";
	private static Properties prop;
	
	public static Connection getConnection() throws SQLException
	{
		// read in the properties from the file
		try
		{
			prop = new Properties();
			prop.load(new FileInputStream(FILE_NAME));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// get the credentials from the Properties object
		String url = prop.getProperty("url");	
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		
		// return the Connection via DriverManager
		return DriverManager.getConnection(url, username, password);
	}
}
