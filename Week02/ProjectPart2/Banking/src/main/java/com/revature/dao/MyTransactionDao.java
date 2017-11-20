package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.objects.Transaction;
import com.revature.util.ConnectionUtil;

public class MyTransactionDao implements TransactionDao {
	private Statement myStatement = null;
	
	public void newTransaction(Transaction transaction) {
		try(Connection conn = ConnectionUtil.getConnection();){			
			
			String sql = "INSERT INTO BANK_TRANSACTION(transactDate, details, amount, accountNber) VALUES(SYSDATE" + 
				    ", '" + transaction.getDetails() + "', " + transaction.getAmount() + ", '" + 
				    transaction.getAccountNber() + "')";
			
			myStatement = conn.createStatement();
			int created = myStatement.executeUpdate(sql);
			
			System.out.println(created + " transaction created");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public Transaction findTransaction(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Transaction transact = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM BANK_TRANSACTION WHERE transactid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()){

				transact = new Transaction( 
						rs.getInt("TRANSACTID"), 
						rs.getDate("TRANSACTDATE"), 
						rs.getString("DETAILS"), 
						rs.getDouble("AMOUNT"), 
						rs.getString("ACCOUNTNBER")
						);				
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return transact;
	}

	public ArrayList<Transaction> listTransactionsAccount(String accountNber) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Transaction> listTransact = new ArrayList<>();

		try(Connection conn = ConnectionUtil.getConnection();){
		String sql = "SELECT * FROM BANK_TRANSACTION WHERE accountNber = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, accountNber);
		rs = ps.executeQuery();
		
		while(rs.next()){

			listTransact.add(new Transaction( 
					rs.getInt("TRANSACTID"), 
					rs.getDate("TRANSACTDATE"), 
					rs.getString("DETAILS"), 
					rs.getDouble("AMOUNT"), 
					rs.getString("ACCOUNTNBER")
					));				
		}
		
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return listTransact;
		}

	public ArrayList<Transaction> listAllTransactions() {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Transaction> listTransact = new ArrayList<>();

		try(Connection conn = ConnectionUtil.getConnection();){
		String sql = "SELECT * FROM BANK_TRANSACTION";
		ps = conn.prepareStatement(sql);
		//ps.setString(1, accountNber);
		rs = ps.executeQuery();
		
		while(rs.next()){

			listTransact.add(new Transaction( 
					rs.getInt("TRANSACTID"), 
					rs.getDate("TRANSACTDATE"), 
					rs.getString("DETAILS"), 
					rs.getDouble("AMOUNT"), 
					rs.getString("ACCOUNTNBER")
					));				
		}
		
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return listTransact;
		}

	
	public int deleteTransaction(int transactionNber) {
		// TODO Auto-generated method stub
		return 0;
	}

}
