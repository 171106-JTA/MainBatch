package com.bankoftheapes.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.User;
import com.bankoftheapes.util.ConnectionUtil;
import static com.bankoftheapes.util.CloseStream.close;;

public class QueryUtil implements BankDao{
	private Connection conn;
	
	@Override
	public boolean userExists(String username) {
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM Customer WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.executeQuery();
			
		}catch(SQLException e) {
			System.out.println("Username taken");
			return true;
		}
		return false;
	}
	
	@Override
	public User getUserInfo(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User u = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM Customer WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				u = new User(rs.getString("Username"), rs.getString("Pass"));
				u.setApproved(rs.getInt("ApprovalStatus"));
				u.setBanned(rs.getInt("LockStatus"));
				u.setAccess_level(rs.getString("AccessLevel"));
				u.setAccId(rs.getInt("C_AccId"));
				u.setLoanId(rs.getInt("C_LoanId"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			close(ps);
			close(rs);
		}
		return u;
	}
	
	@Override
	public BankAccount getAccountInfo(User user) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		BankAccount ba = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM Bank_Account WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			rs = ps.executeQuery();
			while(rs.next()) {
				ba = new BankAccount(user.getAccId());
				ba.setAmount(rs.getDouble("Amount"));
			}
			user.setBankAccount(ba);
		}catch(SQLException e) {
			return null;
		}finally {
			close(rs);
			close(ps);
		}
		return ba;
	}
	
	@Override
	public void updateAccountAmount(BankAccount ba) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call update_account_amount(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, ba.getAccId());
			cs.setDouble(2, ba.getAmount());
			cs.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(cs);
		}
		
	}
}
