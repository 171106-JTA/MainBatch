package com.revature.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CloserUtility {
	public static void close(FileInputStream fis) {
		if(fis != null) {
			try {
				fis.close();
			} catch(IOException e) {
				System.err.println("Could not close properties file input stream.");
				e.printStackTrace();
			}
		}
	}
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {
				System.err.println("Could not close database connection.");
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
	public static void close(PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				System.err.println("Could not close prepared statement.");
				e.printStackTrace();
			} finally {
				ps = null;
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException e) {
				System.err.println("Could not close database result set.");
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
	}
	public static void close(PrintWriter pw) {
		if(pw != null) {
			try {
				pw.close();
			} finally {
				pw = null;
			}
		}
	}
}
