package com.bankoftheapes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bankoftheapes.user.User;
import com.bankoftheapes.util.ConnectionUtil;

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
		}
		return u;
	}
}
