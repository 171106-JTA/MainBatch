package com.revature.project2.connector;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class ConnectorUtil {
	
	
	private static Properties prop;
	private final static String FILE_NAME = "DB.properties";
	
	public static Connection getConnected(){
		Connection conn;
		try{
			prop = new Properties();
			prop.load(new FileInputStream(FILE_NAME));
			Class.forName(prop.getProperty("className"));
			conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"), prop.getProperty("password"));
			return conn;
		}catch(SQLException | IOException | ClassNotFoundException  e){
			e.printStackTrace();
			return null;
		}
	}
	
	
}
