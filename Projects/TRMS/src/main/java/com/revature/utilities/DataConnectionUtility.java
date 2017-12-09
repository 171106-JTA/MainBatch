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
			try {
				propertiesfile = new FileInputStream("C:\\Users\\Jeff\\Documents\\Revature\\Projects\\TRMS\\database.properties");
			} catch(IOException e) {
				System.err.println("Could not load properties file.");
				throw e;
			}
			new Properties().load(propertiesfile);
			CloserUtility.close(propertiesfile);
			properties = System.getenv("DBProps").split(";");
			dataconnection = new DataConnectionUtility();
		}
		return dataconnection;
	}
	public boolean initializeConnection() throws SQLException {
		if(connection == null) {
			try {
				System.out.println(properties[1]);
				System.out.println(properties[2]);
				System.out.println(properties[3]);
				connection = DriverManager.getConnection(properties[1],properties[2],properties[3]);
			} catch(SQLException e) {
				System.err.println("Could not connect to database.");
				CloserUtility.close(connection);
				throw e;
			}
			return true;
		}
		return false;
	}
	public ResultSet requestQuery(String sqlquery) throws SQLException {
		try {
			preparedstatement = connection.prepareStatement(sqlquery);
			resultset = preparedstatement.executeQuery();
		} catch (SQLException e) {
			System.err.println("Could not query database.");
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
			throw e;
		}
		System.out.println("Database query result.");
		System.out.println(resultset.toString());
		try{
			return resultset;
		} finally {
			CloserUtility.close(resultset);
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
		}
	}
	public int requestQuery(String sqlquery, String commit) throws SQLException {
		try {
			preparedstatement = connection.prepareStatement(sqlquery + " " + commit);
		} catch (SQLException e) {
			System.err.println("Could not prepare statement.");
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
			throw e;
		}
		rowsaffected = preparedstatement.executeUpdate();
		System.out.println("Database query result.");
		System.out.println(resultset.toString());
		try{
			return rowsaffected;
		} finally {
			CloserUtility.close(resultset);
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
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
		sql += ";";
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
			CloserUtility.close(resultset);
			CloserUtility.close(preparedstatement);
			CloserUtility.close(connection);
		}
	}
}
