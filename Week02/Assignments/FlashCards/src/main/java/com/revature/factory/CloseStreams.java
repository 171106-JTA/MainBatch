package com.revature.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseStreams {
	public static void close(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet res) {
		try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(FileInputStream fis) {
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
