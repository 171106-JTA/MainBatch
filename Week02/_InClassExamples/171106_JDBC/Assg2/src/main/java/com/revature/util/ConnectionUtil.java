package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	private static Properties prop; //Make our property reference
	private final static String FILE_NAME = "DB.properties";
	final static String URL = "url";
	final static String USER = "user";
	final static String PASS = "pass";
	final static String CLASS = "class";

	
	public static Connection getConnection() throws SQLException{
		try{
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Populate properties through a file.
//		String url = prop.getProperty(URL);
//		String username = prop.getProperty(USER);
//		String password = prop.getProperty(PASS);
//		try {
//			Class.forName(prop.getProperty(CLASS));
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} //OPTIONAL, some may need to do this
		
		//Populate properties the safe way.
		 

		System.out.println(System.getenv());
		
		String props[] = System.getenv("DBProps").split(";");
		try {
			Class.forName(prop.getProperty(props[3]));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(props[0], props[1], props[2]);
	}
}
