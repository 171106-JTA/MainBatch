package com.revature.servlets.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection("jdbc:oracle:thin:@sandbox171106.c7gydzn7nvzj.us-east-1.rds.amazonaws.com:1521:orcl","jordan", "jordan1234");
	}
}
