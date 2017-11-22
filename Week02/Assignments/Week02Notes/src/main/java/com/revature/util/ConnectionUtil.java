package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	private static Properties prop;
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException
	{
		try {
			prop = new Properties();
			prop.load(new FileInputStream(FILE_NAME));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		/*String url = prop.getProperty("url");	
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		*/
		String[] props = System.getenv("DBProperties").split(";");
		return DriverManager.getConnection(props[1], props[2], props[3]);	
	}
}