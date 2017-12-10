package com.revature.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataConnectionUtility {
	private static DataConnectionUtility dataconnection;
	private static String properties[];
	private static FileInputStream propertiesfile;
	private static Connection connection;
	private static PreparedStatement preparedstatement;
	private static ResultSet resultset;
	private static int rowsaffected;
	private DataConnectionUtility(){
	}
	public static DataConnectionUtility getDataConnection() throws IOException {
		if(dataconnection == null) {
	//		try {
	//			propertiesfile = new FileInputStream("C:\\Users\\Jeff\\Documents\\Revature\\Projects\\TRMS\\database.properties");
	//		} catch(IOException e) {
	//			System.err.println("Could not load properties file.");
	//			throw e;
	//		}
	//		new Properties().load(propertiesfile);
	//		CloserUtility.close(propertiesfile);
			properties = System.getenv("DBProps").split(";");
			dataconnection = new DataConnectionUtility();
		}
		return dataconnection;
	}
	public boolean initializeConnection() throws SQLException {
		if(connection == null) {
			try {
				 try {
			            Class.forName ("oracle.jdbc.driver.OracleDriver");
			        } catch (ClassNotFoundException e) {
			            System.out.println(e.getMessage());
			        }
				connection = DriverManager.getConnection(properties[1],properties[2],properties[3]);
			} catch(SQLException e) {
				System.err.println("Could not connect to database.");
				e.printStackTrace();
				CloserUtility.close(connection);
				throw e;
			}
			return true;
		}
		return false;
	}
	public ResultSet requestQuery(String sqlquery) throws SQLException {
		try {
			System.out.println(sqlquery);
			preparedstatement = connection.prepareStatement(sqlquery);
			resultset = preparedstatement.executeQuery();
		} catch (SQLException e) {
			System.err.println("Could not query database.");
			e.printStackTrace();
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
			throw e;
		}
		try{
			return resultset;
		} finally {
			connection = null;
		}
	}
	public int requestQuery(String sqlquery, String commit) throws SQLException {
		System.out.println(sqlquery);
		System.out.println(commit);
		try {
			System.out.println(connection);
			preparedstatement = connection.prepareStatement(sqlquery);
		} catch (SQLException e) {
			System.err.println("Could not prepare statement.");
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
			throw e;
		}
		rowsaffected = preparedstatement.executeUpdate();
		preparedstatement = connection.prepareStatement(commit);
		preparedstatement.executeUpdate();
		System.out.println("Database query result.");
		System.out.println(rowsaffected);
		try{
			return rowsaffected;
		} finally {
			connection = null;
		}
	}
	public boolean exists(String table, String[] columns, String[] ids) throws SQLException { // NOTE: Fix This
		String sql = "SELECT * FROM " + table + " WHERE ";
		for(int i = 0; i < columns.length; i++) {
			sql += columns[i] + " = " + ids[i];
			if((i + 1) < columns.length) {
				sql += " AND ";
			}
		}
		System.out.println(sql);
		try {
			preparedstatement = connection.prepareStatement(sql);
			resultset = preparedstatement.executeQuery();
		} catch (SQLException e) {
			System.err.println("Could not query database.");
			CloserUtility.close(resultset);
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
			throw e;
		}
		try{
			while(resultset.next()) {
				return true;
			}
			return false;
		} finally {
			connection = null;
		}
	}
}
