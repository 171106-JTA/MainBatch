package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankAccount.User;
import com.revature.util.ConnectionUtil;

public class UserDaoImplement implements UserDao {
	public boolean createUser(User user) {
		CallableStatement cs = null;
		ResultSet rs = null;
		boolean userInserted = false;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String username = user.getUsername(), firstname = user.getFirstName(), lastname = user.getLastName(),
					middleInitial = user.getMiddleInitial(), password = user.getPassword();
			int status = user.getStatus(), permission = user.getPermissions();
			double accountAmount = user.getAccountAmount();
			String sql = "{call insert_user(?,?,?,?,?,?,?,?)";

			// Store data in list and user a loop here
			cs = conn.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, firstname);
			cs.setString(3, lastname);
			cs.setString(4, middleInitial);
			cs.setString(5, password);
			cs.setInt(6, permission);
			cs.setInt(7, status);
			cs.setDouble(8, accountAmount);

			rs = cs.executeQuery();

			userInserted = true;

		} catch (SQLException e) {
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			// e.printStackTrace();
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return userInserted;
	}

	public User getUserCheckPassword(String username, String password) {
		User retUser = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM A_USER " + "WHERE USERNAME = ? AND USER_PASSWORD = ?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				String usrname = rs.getString("username"), firstname = rs.getString("firstname"),
						lastname = rs.getString("lastname"), middleinitial = rs.getString("middleinitial"),
						passwd = rs.getString("user_password");
				int status = rs.getInt("status"), permission = rs.getInt("permission");
				double accountAmount = rs.getDouble("accountAmount");
				retUser = new User(usrname, firstname, lastname, middleinitial, passwd, permission, status,
						accountAmount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retUser;
	}

	public User getUser(String username) {
		User retUser = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM A_USER " + "WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			rs = ps.executeQuery();

			if (rs.next()) {
				String usrname = rs.getString("username"), firstname = rs.getString("firstname"),
						lastname = rs.getString("lastname"), middleinitial = rs.getString("middleinitial"),
						passwd = rs.getString("user_password");
				int status = rs.getInt("status"), permission = rs.getInt("permission");
				double accountAmount = rs.getDouble("accountAmount");
				retUser = new User(usrname, firstname, lastname, middleinitial, passwd, permission, status,
						accountAmount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retUser;
	}

	public List<String> getUsersConditionally(final int status, final int permissions) {
		List<String> username_list = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT USERNAME FROM A_USER " + "WHERE STATUS = ? AND PERMISSION = ?";
			ps = conn.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, permissions);
			rs = ps.executeQuery();

			while (rs.next()) {
				username_list.add(rs.getString("USERNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return username_list;
	}

	public boolean alterUserStatusAndPermission(String username, final int currentStatus, final int currentPermission, 
			final int newStatus, final int newPermission) {
		CallableStatement cs = null;
		ResultSet rs = null;
		boolean userAltered = false;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "{call alter_user(?,?,?,?,?)";

			// Store data in list and user a loop here
			cs = conn.prepareCall(sql);
			cs.setString(1, username); // Current Username
			cs.setInt(2, currentStatus); // New Username
			cs.setInt(3, currentPermission);
			cs.setInt(4, newStatus);
			cs.setInt(5, newPermission);
			rs = cs.executeQuery();

			userAltered = true;
		} catch (SQLException e) {
			System.out.println("alterUserStatusAndPermission Exception");
			// To Do: This catch statement executes if user was not inserted into the
			// database.
			// How to return the stacktrace to Driver to be logged???
			e.printStackTrace();
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return userAltered;
	}
}
