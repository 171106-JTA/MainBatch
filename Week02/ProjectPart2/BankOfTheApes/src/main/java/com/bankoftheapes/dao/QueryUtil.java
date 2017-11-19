package com.bankoftheapes.dao;

import static com.bankoftheapes.util.CloseStream.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.Loan;
import com.bankoftheapes.user.User;
import com.bankoftheapes.util.ConnectionUtil;;

public class QueryUtil implements BankDao{
	private Connection conn;
	
	@Override
	public boolean userExists(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String user = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM Customer WHERE USERNAME = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = rs.getString("USERNAME");
			}
			if(user == null) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(ps);
		}
		return true;
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
	
	@Override
	public void addNewUser(User user) {
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO Customer "
					+ "(USERNAME, PASS) "
					+ "VALUES (?, ?)";
			ps  = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			commitChanges();
			close(ps);
		}
	}
	
	@Override
	public void showAllUsers() {
		Statement stmt = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT USERNAME, APPROVALSTATUS, LOCKSTATUS, ACCESSLEVEL FROM Customer";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getString("USERNAME") + " : " + rs.getString("APPROVALSTATUS")
				+ " : " + rs.getString("LOCKSTATUS") + " : " + rs.getString("ACCESSLEVEL"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
		
	}
	
	@Override
	public void updateApproval(User u) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call update_approval_lock(?, ?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, u.getName());
			cs.setInt(2, u.isApproved());
			cs.setInt(3, u.isBanned());
			cs.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(cs);
		}
	}
	
	@Override
	public void updateAccessStatus(User u) {
		CallableStatement cs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{call promote_user(?, ?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, u.getName());
			cs.setString(2, u.getAccess_level());
			cs.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(cs);
		}
		
	}
	
	@Override
	public void applyLoan(User user) {
		PreparedStatement ps = null;
		Loan loan = user.getLoan();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO Loan "
					+ "(USERNAME, LOAN_AMOUNT, REQUESTDATE) "
					+ "VALUES (?, ?, ?)";
			ps  = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setDouble(2, loan.getAmount());
			ps.setString(3, loan.getDate());
			ps.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			commitChanges();
			close(ps);
		}
		
	}
	
	@Override
	public void showLoans(User user) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT LOANID, LOAN_AMOUNT, STATUS, PAIDOFF FROM Loan WHERE USERNAME = ?";
			ps  = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("LOANID") + " " + rs.getDouble("LOAN_AMOUNT") + 
						" " + rs.getString("STATUS") + " " + rs.getString("PAIDOFF"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			commitChanges();
			close(ps);
		}
	}
	
	@Override
	public void updateLoan(User user, int loanId, String status, String approvalDate) {
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE LOAN SET STATUS = ?, APPROVALDATE = ?" +
						" WHERE USERNAME = ? AND LOANID = ?";
			ps  = conn.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, approvalDate);
			ps.setString(3, user.getName());
			ps.setInt(4, loanId);
			ps.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			commitChanges();
			close(ps);
		}
	}		
	
	private void commitChanges() {
		Statement stmt = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "COMMIT";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
		}
	}
}
