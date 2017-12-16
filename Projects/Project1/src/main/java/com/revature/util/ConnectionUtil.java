package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import oracle.jdbc.driver.OracleDriver;

public class ConnectionUtil {
	private static Properties prop; //Make our property reference
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException{
		/*try{
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//Populate properties through a file.
		String url      = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		return DriverManager.getConnection(url, username, password);*/
		
		/*//Populate properties the safe way.
		 
		
		String props[] = System.getenv("DBProps").split(";");
		return DriverManager.getConnection(props[1],props[2], props[3]);*/
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", 
				"miwarren", "Data0x42617365");
	}
}
