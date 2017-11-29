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
	
	public static Connection getConnection() throws SQLException{
		/*try{
			prop = new Properties(); //Instantiate our property object
			prop.load(new FileInputStream(FILE_NAME)); //Use a filestream to populate properties
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		//Populate properties through a file.
/*		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		Class.forName(prop.getProperty("class")); //OPTIONAL, some may need to do this
*/		
		//Populate properties the safe way.
		 
		
/*		String props[] = System.getenv("DBProps").split(";");
		return DriverManager.getConnection(props[1],props[2], props[3]);*/
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", 
				"dontcare",
				"p4ssw0rd");
	}
}
