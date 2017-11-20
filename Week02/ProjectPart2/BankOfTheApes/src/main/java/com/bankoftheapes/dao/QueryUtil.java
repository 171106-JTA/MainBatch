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
	
	/**
	 * This method checks for the existence of a user. Used to find username availability.
	 * Utilizes a preparedstatement to query the DB.
	 * 
	 * @param String username - the username to find
	 * 
	 * @return Boolean true if the username is taken, otherwise false
	 */
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
	
	/**
	 * This method gets the info of a user from the customer table. Utilizes a preparedstatement to query the DB.
	 * 
	 * @param String username - the username to be find
	 * 
	 * @return User a User object that will passed around in the program
	 */
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
	
	/**
	 * This method gets the user's account info from the account table. Utilizes a preparedstatement.
	 * 
	 * @param User user - object to set the bank account to
	 * 
	 * @return BankAccount the actual account of the user
	 */
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
	
	/**
	 * This method updates the bankaccount's amount. Utilizes a callablestatement.
	 * 
	 * @param BankAccount ba - the BankAccount object that hold the temporary values
	 * 
	 * 
	 */
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
	
	/**
	 * This method addes a new user to the customer table.
	 * 
	 * @param User user - the temporary user object that holds the customer info
	 * 
	 */
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
	
	/**
	 * Shows all the users in the database
	 * 
	 */
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
	
	/**
	 * THis method is used to update the approval status of a customer.
	 * 
	 * @param User u - the User object holding the temporary information
	 * 
	 */
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
	
	/**
	 * Method to promote a user to admin or mod.
	 * 
	 * @param User u - object that holds the temporary values to be used in the program.
	 * 
	 */
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
	
	/**
	 * Method used to apply for a loan.
	 * 
	 * @param User user - holds the temporary data to be used in the program
	 * 
	 */
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
	
	/**
	 * Shows all the loans of a user
	 * 
	 * @param User user - object needed to find the loans of a user
	 * 
	 */
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

	/**
	 * Commits changes to the database.
	 * 
	 */
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
