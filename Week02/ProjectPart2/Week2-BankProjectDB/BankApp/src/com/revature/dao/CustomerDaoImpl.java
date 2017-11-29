package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Administrator;
import com.revature.beans.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerDaoImpl {
	
	private static final Logger log = Logger.getLogger("GLOBAL");
	
	private Statement stmt = null;
	
	public void createCust(Customer cust){
		int isBlocked = 0;
		if (cust.isBlocked()){
			isBlocked = 1;
		} else {
			isBlocked = 0;
		}
		try(Connection conn = ConnectionUtil.getConnection();){	
			String sql = "INSERT INTO Customer (custid, username, password, firstname, " +
						 					   "lastname, ssn, acctnumber, isBlocked) " + 
				"VALUES('" + cust.getCustID() + "', '" +
						 	 cust.getUsername() + "', '" + cust.getPassword() + "', '" + 
						     cust.getFirstName() + "', '" + cust.getLastName() + "', '" + 
				             cust.getSsn() + "', '" + cust.getAccount().getAcctNumber() + "', '" +
				             isBlocked +
				             "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
		
	public void deleteCust (Customer cust){
		try(Connection conn = ConnectionUtil.getConnection();){	
			String sql = "DELETE FROM Customer " + 
					     "WHERE username = '" + cust.getUsername() + "'";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected + " Rows affected");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	public void blockCust(Customer cust){
		try(Connection conn = ConnectionUtil.getConnection();){	
			String sql = "UPDATE Customer " +
						 "SET isBlocked = " + 1 +
						 "WHERE username = '" + cust.getUsername() + "'";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected + " Rows affected");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	
	public void unblockCust(Customer cust){
		try(Connection conn = ConnectionUtil.getConnection();){	
			String sql = "UPDATE Customer SET isblocked = " + 0 +
						" WHERE username = '" + cust.getUsername() + "'";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			//System.out.println(affected + " Rows affected");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	
	public int getIndex(){
		int result = 0;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT COUNT(*) FROM customer ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				result = rs.getInt("COUNT(*)");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
		return result + 1;
	}
	
	public ArrayList<Customer> getCustList(){
		ArrayList<Customer> result = new ArrayList<Customer>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM customer ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				String firstname = rs.getString("FIRSTNAME");
				String lastname = rs.getString("LASTNAME");
				String ssn = rs.getString("SSN");
				String username = rs.getString("USERNAME");
				String password = rs.getString("PASSWORD");
				int isBlocked = rs.getInt("ISBLOCKED");
				int acctNumber = rs.getInt("ACCTNUMBER");
				Customer newCust = new Customer(firstname, lastname, ssn, username, password, isBlocked);
				newCust.setAccount(new AccountDaoImpl().selectAcctById(acctNumber));
				result.add(newCust);
			}
		}catch(SQLException e){
			log.error("CustomerDaoImpl: Error loading Records", e);
		}finally{
			close(stmt);
		}
		return result;
	}
	
}
