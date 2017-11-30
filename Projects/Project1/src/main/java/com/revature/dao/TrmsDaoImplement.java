package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.bean.User;
import com.revature.util.ConnectionUtil;

public class TrmsDaoImplement implements TrmsDao{

	public ArrayList<User> login(String username, String password) {
		System.out.println("In the Login Dao");
		
		int status = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<User> allUsers = new ArrayList<User>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			
//			String sql = "SELECT status FROM evan.Login WHERE username=? AND password=?";
			String sql = "SELECT * FROM Login";
			ps = conn.prepareStatement(sql);
//			ps.setString(1, username);
//			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
//			if(rs.next()) {
//				status = rs.getInt("permission");
//			}
			System.out.println("Username\tPassword\tPermissions");
			while(rs.next()) {
				String theUsername = rs.getString("USERNAME");
				String thePassword = rs.getString("PASSWORD");
				int permission = rs.getInt("PERMISSION");
				System.out.println(theUsername + "\t" + thePassword + "\t" + permission);
				allUsers.add(new User(theUsername, thePassword, permission));
			}

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

//		return status;
		return allUsers;
	}

}
