package com.revature.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties prop;
	private final static String FILE_NAME = "DB.properties";

	public static Connection getConnection() throws SQLException {
		System.out.println("in the connection util!");

		// Variables for establishing database connection
		String dbName = null, userName = null, password = null, hostname = null, port = null;

		// Get system variables, if they exist (used for local development)
		if (System.getenv("RDS_HOSTNAME") != null) {
			System.out.println("Fetching Environment Variables");
			dbName = System.getenv("RDS_DB_NAME");
			userName = System.getenv("RDS_USERNAME");
			password = System.getenv("RDS_PASSWORD");
			hostname = System.getenv("RDS_HOSTNAME");
			port = System.getenv("RDS_PORT");
		// Otherwise, get property variables, if they exist (used in Elastic Beanstalk deployment)
		} else if (System.getProperty("RDS_HOSTNAME") != null) {
			System.out.println("Fetching Property Variables");
			dbName = System.getProperty("RDS_DB_NAME");
			userName = System.getProperty("RDS_USERNAME");
			password = System.getProperty("RDS_PASSWORD");
			hostname = System.getProperty("RDS_HOSTNAME");
			port = System.getProperty("RDS_PORT");
		}
		return getConnectionHelper(hostname, port, dbName, userName, password);
	}

	private static Connection getConnectionHelper(String hostname, String port, String dbName, String userName,
			String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// logger.trace("Getting remote connection with connection string from
			// environment variables.");
			String url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + dbName;
			Connection con = DriverManager.getConnection(url, userName, password);
			// logger.info("Remote connection successful.");
			return con;
		} catch (ClassNotFoundException e) {
			// logger.warn(e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			// logger.warn(e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
