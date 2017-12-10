package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.revature.logging.LoggingService;

public class ConnectionUtil {
	//private static Properties prop; //Make our property reference
	//private final static String FILE_NAME = "DB.properties";
	private static Connection connection = null;
	private static ConnectionUtil cUtil = null;
	
	private ConnectionUtil() {
	}
	
	public Connection getConnection() throws SQLException{
		connection = null;
		
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("usr"),
					prop.getProperty("pwd"));
		} catch (IOException e) {
			LoggingService.getLogger().warn("Couldn't read database.properties");
			System.out.println("Couldn't find properties");
		} catch (SQLException e) {
			LoggingService.getLogger().warn("Couldn't connect to DB", e);
			System.out.println("Could not connect to DB");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public static ConnectionUtil getInstance() {
		if (cUtil == null) {
			cUtil = new ConnectionUtil();
		}
		return cUtil;
	}
}
