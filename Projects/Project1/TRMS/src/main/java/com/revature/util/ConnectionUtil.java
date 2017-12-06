package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException{
		String props[]= System.getenv("TRMS").split(";");//create environment variable dbprops and then use it to fill variables
		return DriverManager.getConnection(props[1],props[2],props[3]);
	}
}
