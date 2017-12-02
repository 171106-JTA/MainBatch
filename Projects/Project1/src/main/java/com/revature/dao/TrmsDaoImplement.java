package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.bean.User;
import com.revature.util.ConnectionUtil;

public class TrmsDaoImplement implements TrmsDao{

	@Override
	public String login(String username, String password) {
		System.out.println("In the Login Dao");
		
		String permission = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM Login WHERE username=? AND password=?";
//			String sql = "SELECT * FROM Login";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			System.out.println("Testing Result");
			if(rs.next()) {
				System.out.println("At least 1 result!!!");
				username = rs.getString("username");
				password = rs.getString("password");
				permission = rs.getString("permission");
				System.out.println("username: " + username + ", password: " + password + ", permission: " + permission);
			}
//			 while(rs.next()) {
//				 String theUsername = rs.getString("USERNAME");
//				 String thePassword = rs.getString("PASSWORD");
//				 permission = rs.getString("PERMISSION");
//				 System.out.println(theUsername + "\t" + thePassword + "\t" + permission);
//			 }
		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			 e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return permission;
	}

}
