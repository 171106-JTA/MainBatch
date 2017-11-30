package com.revature.dao.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Creates and manages a set of database connections throughout the life of the application
 * @author Antony Lulciuc
 */
public final class ConnectionUtil {
	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	public static void close(Closeable stream) {
		if (stream != null) {
			try {
				logger.debug("attempting to clsoe stream");;
				stream.close();
				logger.debug("closed stream");
			} catch(IOException e) {
				e.printStackTrace();
				logger.debug("failed to close stream, message: " + e.getMessage());
			}
		}
	}
	
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				logger.debug("attempting to clsoe stream");;
				connection.close();
				logger.debug("closed stream");
			} catch(SQLException e) {
				e.printStackTrace();
				logger.debug("failed to close stream, message: " + e.getMessage());
			}
		}
	}
	
	public static void close(ResultSet res) {
		if (res != null) {
			try {
				logger.debug("attempting to clsoe stream");;
				res.close();
				logger.debug("closed stream");
			} catch(SQLException e) {
				e.printStackTrace();
				logger.debug("failed to close stream, message: " + e.getMessage());
			}
		}
	}
	
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				logger.debug("attempting to clsoe stream");;
				statement.close();
				logger.debug("closed stream");
			} catch(SQLException e) {
				e.printStackTrace();
				logger.debug("failed to close stream, message: " + e.getMessage());
			}
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		String[] prop;
		
		logger.debug("attempting to open connection to database");
		
		try {
			prop = System.getenv("DBProp").split(";");
			Class.forName(prop[0]);
			conn = DriverManager.getConnection(prop[1], prop[2], prop[3]);
			logger.debug("opened connection to database");
		} catch (ArrayIndexOutOfBoundsException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("failed to get connection to database, message:" + e.getMessage());
		}
		
		return conn;
	}
}
