package main.java.com.revature.p1dao;

import static main.java.com.revature.p1util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import main.java.com.revature.p1.User;
import main.java.com.revature.p1util.ConnectionUtil;

public class BankAccountDaoImpl implements BankAccountDao {
	private Statement stmt = null;
	
	public int boolToInt(Boolean b) {
		if (b) {
			return 1;
		}
		return 0;
	}
		
	@Override
	public void createUser(User u) {
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "INSERT INTO ADMIN.BANK_USER(USER_NAME, USER_USER, USER_PASS)" + 
		"VALUES('" + u.getName() + "', '" + u.getUser() + "', '" + u.getPass() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + "Rows Added");
		} catch (SQLIntegrityConstraintViolationException e) {
			
	}
		catch (SQLException e) {
			//e.printStackTrace();
			
		} finally {
			close(stmt);
		}
		
	}
	
	public void updateUser(User u) {
		CallableStatement cs = null;
		int affected = 0;
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "{CALL UPDATE_USER_BY_USER(?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, u.getUser());
			cs.setLong(2, (long) u.getBal());
			cs.setInt(3, boolToInt(u.isApproved()));
			cs.setInt(4, boolToInt(u.isAdmin()));
			cs.execute();
			affected ++;
			System.out.println(affected + " row updated.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public User selectUser(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User u = null;
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "SELECT * FROM BANK_USER WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				u = new User(rs.getString("USER_NAME"), rs.getString("USER_USER"),
						rs.getString("USER_PASS"), rs.getDouble("USER_BAL"),
						rs.getBoolean("USER_APPROVED"), rs.getBoolean("USER_ADMIN"));
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		return u;
	};
	
	@Override
	public void deleteUser(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int affected = 0;
		try (Connection conn = ConnectionUtil.getConnection();) {
			String sql = "DELETE FROM BANK_USER WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			affected++;
			System.out.println(affected  + " row affected.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
			close(rs);
		}
		
	}
}
