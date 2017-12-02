package com.banana.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{

		try {
	        Class.forName ("oracle.jdbc.OracleDriver");
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    	}
		//safest way to populate properties
		String props[] = System.getenv("DBProp").split(";");
		
		return DriverManager.getConnection(props[0], props[1], props[2]);
	}
}
