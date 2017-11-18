package main.java.com.revature.p1util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "Admin";
		String password = "p4ssw0rd";
		
		return DriverManager.getConnection(url, username, password);
	}
}
