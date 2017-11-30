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
		// try {
		// // Path currentRelativePath = Paths.get("");
		// // String s = currentRelativePath.toAbsolutePath().toString();
		// // System.out.println("Current relative path is: " + s);
		//
		// System.out.println(System.getProperty("user.dir"));
		//
		// prop = new Properties(); // Instantiate our property object
		// prop.load(new FileInputStream(FILE_NAME)); // User a filestream to populate
		// properties
		//
		// FileReader reader = new FileReader(FILE_NAME);
		// prop.load(reader);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// String url = prop.getProperty("url");
		// String username = prop.getProperty("username");
		// String password = prop.getProperty("password");
		// System.out.println("url " + url + "\nusername " + username + "\npassword "
		// + password);
		// String prop[] = System.getenv("DBProps").split(";");
		// System.out.println("prop[1] " + prop[1] + "\nprop[2] " + prop[2] + "\nprop[3]
		// "
		// + prop[3]);
		// return DriverManager.getConnection(url, username, password);
		// return DriverManager.getConnection(prop[1], prop[2], prop[3]);
		
		//Remove URL --> for git
		//Remove Username --> for git
		//Remove Password --> for git
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Class.forName exception!!!");
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
}
