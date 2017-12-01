package com.revature.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","polkaman", "polkaman");
	}
}
