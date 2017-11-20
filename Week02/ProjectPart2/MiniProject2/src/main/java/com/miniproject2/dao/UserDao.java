package com.miniproject2.dao;

import static com.miniproject2.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.miniproject2.main.User;
import com.miniproject2.util.ConnectionUtil;

/* Just for my own convenience.
 * CREATE TABLE users
(
    username varchar2(20),
    pw  varchar2(20),
    balance float(53),
    activated varchar(1),
    locked varchar(1),
    admin varchar(1),
    constraint pk_username primary key (username)

);
 */
public class UserDao {
	private Statement stmt = null;

	public int userExist(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT count(username) "
					+ "FROM users " + "WHERE username = '" + username + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				// System.out.println(rs);
				count = Integer.parseInt(rs.getString("count(username)"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
		return count;

	}

	public void createUser(String username, String password) {

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO users " + "VALUES('" + username + "', '" + password + "', 0, 0, 0 , 0)";
			stmt = conn.createStatement();
			ResultSet affected = stmt.executeQuery(sql);
			// System.out.println(affected);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public User getUser(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User currUser = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM users " + "WHERE username = '" + username + "'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// System.out.println(rs);
				currUser = new User(rs.getString("username"), rs.getString("pw"));
				currUser.setBalance(Double.parseDouble(rs.getString("balance")));
				currUser.setActivated(Integer.parseInt(rs.getString("activated")));
				currUser.setLocked(Integer.parseInt(rs.getString("locked")));
				currUser.setAdmin(Integer.parseInt(rs.getString("admin")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}

		return currUser;
	}
	
	public void updateUser(User currUser) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE users "
					+ " SET balance = " + currUser.getBalance() + ", "
					+ " activated = " + currUser.isActivated() + ", "
					+ " locked = " + currUser.isLocked() + ", "
					+ " admin = " + currUser.isAdmin()
					+ " WHERE username = '" + currUser.getName() + "'";
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
	
	public List<User> getActList(){
		List<User> actList = new ArrayList<User>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User currUser = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM users "
					+ "WHERE activated = 0";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				// System.out.println(rs);
				currUser = new User(rs.getString("username"), rs.getString("pw"));
				currUser.setBalance(Double.parseDouble(rs.getString("balance")));
				currUser.setActivated(Integer.parseInt(rs.getString("activated")));
				currUser.setLocked(Integer.parseInt(rs.getString("locked")));
				currUser.setAdmin(Integer.parseInt(rs.getString("admin")));
				actList.add(currUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
		return actList;
	}
}
