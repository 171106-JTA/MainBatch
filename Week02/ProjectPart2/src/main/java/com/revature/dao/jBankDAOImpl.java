package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
// import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.account.User;
import com.revature.model.account.UserLevel;
import com.revature.model.transaction.Transaction;
import com.revature.util.ConnectionUtil;

public class jBankDAOImpl implements jBankDAO {
	private Statement stmt = null;

	public void createUser(User usr) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO agni.jbank_users(USERNAME, FIRSTNAME, LASTNAME, USERPWD, USERLEVEL , PIN, LOCKED_TF, APPROVED_TF, BALANCE)"
					+ "VALUES('" + usr.getUsername() + "', '" + usr.getFirstName() + "', '" + usr.getLastName() + "', '"
					+ usr.getPassword() + "', '" + usr.getUserLevel() + "', '" + usr.getPin() + "', '" + usr.isLocked()
					+ "', '" + usr.isApproved() + "', '" + usr.getBalance() + "')";
			System.out.println(sql);
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected + " Rows affected");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
		}
	}

	public void commitQuery() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "COMMIT";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			close(stmt);
		}
	}

	@Override
	public List<User> getAllUser() {
		List<User> usrs = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jbank_users";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User usr = new User();
				usr.setUid(rs.getInt("USER_ID"));
				usr.setUsername(rs.getString("USERNAME"));
				usr.setFirstName(rs.getString("FIRSTNAME"));
				usr.setLastName(rs.getString("LASTNAME"));
				usr.setPassword(rs.getString("USERPWD"));
				usr.setUserLevel(UserLevel.valueOf(rs.getString("USERLEVEL")));
				usr.setPin(rs.getInt("PIN"));
				usr.setApproved(Boolean.valueOf(rs.getString("APPROVED_TF")));
				usr.setLocked(Boolean.valueOf(rs.getString("LOCKED_TF")));
				usr.setBalance(rs.getDouble("BALANCE"));

				usrs.add(usr);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usrs;
	}

	@Override
	public User grabUser(String username, String pw) {
		User usr = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jbank_users where username = ? AND userpwd = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pw);
			rs = ps.executeQuery();

			// While ResultSet has any results
			while (rs.next()) {
				usr = new User();
				usr.setUid(rs.getInt("USER_ID"));
				usr.setUsername(rs.getString("USERNAME"));
				usr.setFirstName(rs.getString("FIRSTNAME"));
				usr.setLastName(rs.getString("LASTNAME"));
				usr.setPassword(rs.getString("USERPWD"));
				usr.setUserLevel(UserLevel.valueOf(rs.getString("USERLEVEL")));
				usr.setPin(rs.getInt("PIN"));
				usr.setApproved(Boolean.valueOf(rs.getString("APPROVED_TF")));
				usr.setLocked(Boolean.valueOf(rs.getString("LOCKED_TF")));
				usr.setBalance(rs.getDouble("BALANCE"));
			}

		} catch (SQLException e) {
			return null;
		} finally {
			close(ps);
			close(rs);
		}
		return usr;
	}

	@Override
	public User grabUser(String username) {
		User usr = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM jbank_users where username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();

			// While ResultSet has any results
			while (rs.next()) {
				usr = new User();
				usr.setUid(rs.getInt("USER_ID"));
				usr.setUsername(rs.getString("USERNAME"));
				usr.setFirstName(rs.getString("FIRSTNAME"));
				usr.setLastName(rs.getString("LASTNAME"));
				usr.setPassword(rs.getString("USERPWD"));
				usr.setUserLevel(UserLevel.valueOf(rs.getString("USERLEVEL")));
				usr.setPin(rs.getInt("PIN"));
				usr.setApproved(Boolean.valueOf(rs.getString("APPROVED_TF")));
				usr.setLocked(Boolean.valueOf(rs.getString("LOCKED_TF")));
				usr.setBalance(rs.getDouble("BALANCE"));
			}

		} catch (SQLException e) {
			return null;
		} finally {
			close(ps);
			close(rs);
		}
		return usr;
	}

	@Override
	public List<Transaction> getTransactionsByUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int promoteUser(User user, String usrLvl) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		User usr = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE jbank_users SET USERLEVEL = ? WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, usrLvl);
			ps.setInt(2, user.getUid());
			rs = ps.executeQuery();

			while (rs.next()) {
				count++;
				usr.setUid(rs.getInt("USER_ID"));
				usr.setUsername(rs.getString("USERNAME"));
				usr.setFirstName(rs.getString("FIRSTNAME"));
				usr.setLastName(rs.getString("LASTNAME"));
				usr.setPassword(rs.getString("USERPWD"));
				usr.setUserLevel(UserLevel.valueOf(rs.getString("USERLEVEL")));
				usr.setPin(rs.getInt("PIN"));
				usr.setApproved(Boolean.valueOf(rs.getString("APPROVED_TF")));
				usr.setLocked(Boolean.valueOf(rs.getString("LOCKED_TF")));
				usr.setBalance(rs.getDouble("BALANCE"));

			}
			usr.setUid(rs.getInt(1));

		} catch (SQLException e) {

		} finally {
			close(ps);
			close(rs);
		}
		return count;
	}

	@Override
	public int lockUser(User user, boolean status) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		User usr = new User();
		String stat = String.valueOf(status);

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE jbank_users SET LOCKED_TF = ? WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, stat);
			ps.setInt(2, user.getUid());
			rs = ps.executeQuery();

			while (rs.next()) {
				count++;
				usr.setUid(rs.getInt("USER_ID"));
				usr.setUsername(rs.getString("USERNAME"));
				usr.setFirstName(rs.getString("FIRSTNAME"));
				usr.setLastName(rs.getString("LASTNAME"));
				usr.setPassword(rs.getString("USERPWD"));
				usr.setUserLevel(UserLevel.valueOf(rs.getString("USERLEVEL")));
				usr.setPin(rs.getInt("PIN"));
				usr.setApproved(Boolean.valueOf(rs.getString("APPROVED_TF")));
				usr.setLocked(Boolean.valueOf(rs.getString("LOCKED_TF")));
				usr.setBalance(rs.getDouble("BALANCE"));

			}
			usr.setUid(rs.getInt(1));

		} catch (SQLException e) {

		} finally {
			close(ps);
			close(rs);
		}
		return count;
	}

	@Override
	public int approveUser(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE jbank_users SET APPROVED_TF = 'TRUE' WHERE username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			commitQuery();
		} catch (SQLException e) {

		} finally {
			close(ps);
			close(rs);
		}
		// use logger
		System.out.println(username + " has been approved.");
		return count;
	}
}
