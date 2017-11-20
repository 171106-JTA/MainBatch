package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.revature.objects.Loan;
import com.revature.util.ConnectionUtil;

public class MyLoanDao implements LoanDao {
	private Statement myStatement = null;
	
	public void newLoan(Loan loan) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "INSERT INTO LOAN(loanNumber, principal, term, interestRate, approved, isPastDue, accountnber) VALUES(" +
					loan.getLoanNumber() + ", " + loan.getPrincipal() + ", " + loan.getTerm() + ", " + loan.getInterestRate() + ", " + 
					loan.isApproved() + ", " + loan.isPastDue() + ", '" + loan.getAccountNumber() + "')";
								
			myStatement = conn.createStatement();
			
			int created = myStatement.executeUpdate(sql);

			System.out.println(created + " loan created");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public Loan findById(int loanNumber) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Loan loan = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM LOAN WHERE loanNumber = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, loanNumber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				loan = new Loan( 
						rs.getInt("loanNumber"), 
						rs.getDouble("principal"), 
						rs.getInt("term"), 
						rs.getDouble("interestRate"), 
						rs.getInt("approved"), 
						rs.getInt("isPastDue"), 
						rs.getString("accountNber") 
						);			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return loan;
	}

	public Loan findByCustomer(int customerId) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Loan loan = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM LOAN WHERE loanNumber = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, customerId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				loan = new Loan( 
						rs.getInt("loanNumber"), 
						rs.getDouble("principal"), 
						rs.getInt("term"), 
						rs.getDouble("interestRate"), 
						rs.getInt("approved"), 
						rs.getInt("isPastDue"), 
						rs.getString("accountNber") 
						);			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return loan;
	}

	public void approve(int loanNumber) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE LOAN SET APPROVED = 1 WHERE loanNumber = " + loanNumber;
								
			myStatement = conn.createStatement();
			
			int approved = myStatement.executeUpdate(sql);

			System.out.println(approved + " Loan - " + loanNumber  + " was approved");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public void deny(int loanNumber) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE LOAN SET APPROVED = -1 WHERE loanNumber = " + loanNumber;
								
			myStatement = conn.createStatement();
			
			int denied = myStatement.executeUpdate(sql);

			System.out.println(denied + " Loan - " + loanNumber  + " was approved");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public ArrayList<Loan> listOfPendingLoans() {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Loan> listPending = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM LOAN WHERE approved = 0";
			ps = conn.prepareStatement(sql);
			//ps.setInt(1, loanNumber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				listPending.add(new Loan( 
						rs.getInt("loanNumber"), 
						rs.getDouble("principal"), 
						rs.getInt("term"), 
						rs.getDouble("interestRate"), 
						rs.getInt("approved"), 
						rs.getInt("isPastDue"), 
						rs.getString("accountNber") 
						));	
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return listPending;
	}

	public ArrayList<Loan> listOfApprovedLoans() {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Loan> listApproved = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM LOAN WHERE approved = 1";
			ps = conn.prepareStatement(sql);
			//ps.setInt(1, loanNumber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				listApproved.add(new Loan( 
						rs.getInt("loanNumber"), 
						rs.getDouble("principal"), 
						rs.getInt("term"), 
						rs.getDouble("interestRate"), 
						rs.getInt("approved"), 
						rs.getInt("isPastDue"), 
						rs.getString("accountNber") 
						));			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return listApproved;
	}


}
