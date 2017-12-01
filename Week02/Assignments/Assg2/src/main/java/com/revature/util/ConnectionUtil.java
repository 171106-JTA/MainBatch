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
	public static final String URL = "jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:orcl"; 
	public static final String USER = "Sean";
	public static final String PASS = "sean1234";

	
	public static Connection getConnection() throws SQLException{
		try{
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Populate properties through a file.
		String url = prop.getProperty(URL);
		String username = prop.getProperty(USER);
		String password = prop.getProperty(PASS);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //OPTIONAL, some may need to do this
		
		//Populate properties the safe way.
		 
		
//		String props[] = System.getenv("DBProps").split(";");
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
