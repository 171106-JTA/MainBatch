package com.revature.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	
	private static Properties prop; //make our property reference
	private static final String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException {

		try {
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		final String props[] = System.getenv("DBProps").split(";");
		
		return DriverManager.getConnection(props[1], props[2], props[3]);
	}
}