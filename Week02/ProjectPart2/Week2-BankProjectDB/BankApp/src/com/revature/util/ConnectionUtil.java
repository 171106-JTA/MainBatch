package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Properties prop; //Make our property reference
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnection() throws SQLException{
		try{
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Populate properties through a file.
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		//Class.forName(prop.getProperty("class")); //OPTIONAL, some may need to do this
		
		//Populate properties the safe way.
		 
		
		//String props[] = System.getenv("DBProps").split(";");
		
		return DriverManager.getConnection(url, username, password);
	}
				
//		 String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		 String username = "mjsnee";
//		 String password = "p4ssw0rd";
//		 
//		 return DriverManager.getConnection(url, username, password);
//	}

}
