package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties prop; // make property reference
	private final static String FILE_NAME = "DB.properties"; // read by default into home directory,

	public static Connection getConnection() throws SQLException {
		/*
		 * String url =
		 * "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:ORCL";
		 * String username = "mani"; String password = "mani1234";
		 * 
		 * above no longer needed bc "dynamic" user assignment
		 */

		try {
			prop = new Properties(); // instantiate property obj
			prop.load(new FileInputStream(FILE_NAME)); // use filestream to populate properties
		} catch (Exception e) {
			e.printStackTrace();
		}
		// way 1, populate properties through file
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		return DriverManager.getConnection(url, username, password);

		// way 2, populate properties the safe way
		/*
		 * open environment vars
		 * 
		 * set environment vars (how for macs?) variable name: anythingIWant variable
		 * value: class;url;username;password (actual things)
		 * 
		 * then-- StringTokenizer st = null; String props[] =
		 * System.getenv("DBProps").split(";"); return
		 * DriverManager.getConnection(props[1],props[2],props[3]);
		 * 
		 * 
		 * above code is safe to push to github
		 */
	}

	/*
	 * if oracle driver doesn't work, force it w
	 * Class.forName(prop.getProperty("class"));
	 */
}
/*
 * when using hibernate, no longer have to use
 */
