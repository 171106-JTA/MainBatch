package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.revature.objects.Bank_account;
import com.revature.util.ConnectionUtil;

public class MyAccountDao implements AccountDao {
	private Statement myStatement = null;
	
	public void newAccount(Bank_account account) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "INSERT INTO BANK_ACCOUNT(accountNber, creationDate, accTypeID, isActive, customerID) VALUES('" +
					account.getAccountNber() + "', SYSDATE , " + account.getAccTypeID() + ", " + account.isActive() + ", " + 
					account.getCustomerID() + ")";
								
			myStatement = conn.createStatement();
			
			int created = myStatement.executeUpdate(sql);

			System.out.println(created + " customer created");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}

	public Bank_account findById(String accountNber) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		Bank_account account = null;
		
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_account WHERE accountNber = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountNber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				account = new Bank_account( 
						rs.getString("accountNber"), 
						rs.getDate("creationDate"), 
						rs.getInt("accTypeID"), 
						rs.getInt("isActive"), 
						rs.getInt("customerID"), 
						rs.getDouble("balance") 
						);			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return account;
	}

	public void updateBalance(String accountNber, double amount) {
		try(Connection conn = ConnectionUtil.getConnection();){
			
			String sql = "UPDATE BANK_ACCOUNT SET BALANCE = BALANCE + " + amount + " WHERE ACCOUNTNBER = " + accountNber;
								
			myStatement = conn.createStatement();
			
			int updated = myStatement.executeUpdate(sql);

			System.out.println(updated + " account updated");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(myStatement);
		}	
	}
	
	public int deleteAccount(String accountNber) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Bank_account> listOfAllAccounts() {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Bank_account>myAccounts = new ArrayList<>();
	
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_account";
			ps = conn.prepareStatement(sql);
			//ps.setString(1, accountNber);
			rs = ps.executeQuery();
			
			while(rs.next()){
				myAccounts.add( new Bank_account( 
						rs.getString("accountNber"), 
						rs.getDate("creationDate"), 
						rs.getInt("accTypeID"), 
						rs.getInt("isActive"), 
						rs.getInt("customerID"), 
						rs.getDouble("balance") 
						));					
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return myAccounts;
	}
	
	
	@Override	
	public ArrayList<Bank_account> listOfAllAccounts(int customerId) {
		PreparedStatement ps = null;
		ResultSet rs = null; //ResultSets hold query data
		ArrayList<Bank_account>myAccounts = new ArrayList<>();
	
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * FROM Bank_account WHERE customerid = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, customerId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				myAccounts.add( new Bank_account( 
						rs.getString("accountNber"), 
						rs.getDate("creationDate"), 
						rs.getInt("accTypeID"), 
						rs.getInt("isActive"), 
						rs.getInt("customerID"), 
						rs.getDouble("balance") 
						));					
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
			close(rs);
		}
		
		return myAccounts;
	}
	

}
