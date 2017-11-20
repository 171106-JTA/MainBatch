package com.revature.DAO;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.bank.Admin;
import com.revature.bank.Admins;
import com.revature.bank.User;
import com.revature.util.ConnectionUtil;

public class AdminDAOImpl implements AdminDAO
{

	@Override
	public Admins getAdmins() {
		Statement stmt = null;
		ResultSet rs = null;
		
		Admins admins = new Admins();
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql  = "SELECT * FROM admins_view";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				admins.add(new Admin(rs.getInt("ADMIN_ID"),
						rs.getString("USER_NAME"),
						rs.getString("USER_PASS"),
						rs.getString("STATE_NAME")));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(stmt);
			close(rs);
		}
		return admins;
	}

	@Override
	public boolean isAdmin(User user) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		boolean isAdmin = false;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql  = "SELECT * FROM admins_view WHERE user_id = ? OR user_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUserID());
			ps.setString(2, user.getName());
			rs = ps.executeQuery();
			// if we get *any* results, that's good enough for us. 
			isAdmin = rs.next();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}
		return isAdmin;
	}

	@Override
	public Admin getAdminById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Admin admin = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM admins_view WHERE admin_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			admin = new Admin(rs.getInt("ADMIN_ID"),
					rs.getString("USER_NAME"),
					rs.getString("USER_PASS"),
					rs.getString("STATE_NAME"));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}
		return admin;
	}

	@Override
	public void promoteToAdmin(User user) {
		// if user is banned, we shold probably exit this immediately
		if (user.getState().equals(User.BANNED)) 
			throw new IllegalArgumentException("Attempted to promote a banned User.");
		CallableStatement cs = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call promote_by_username(?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, user.getName());
			cs.executeQuery();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
	}

	/**
	 * Removes an Admin from the list of Admins
	 * @param admin : The {@code Admin} to remove
	 * @return the rows effected
	 */
	@Override
	public int demote(Admin admin) {
		CallableStatement cs = null;
		
		int rowsEffected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call demote_by_username(?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, admin.getName());

			rowsEffected = cs.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
		return rowsEffected;
	}

	/** 
	 * Not only removes an Admin from the list of admins, but also bans them as a User.
	 * @param admin : The {@code Admin} to ban
	 */
	@Override
	public int delete(Admin admin) {
		// TODO: Implement this
		return 0;
	}

}
