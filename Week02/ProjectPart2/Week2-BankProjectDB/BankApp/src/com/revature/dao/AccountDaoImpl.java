package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.util.ConnectionUtil;

public class AccountDaoImpl implements AccountDao{
	
	private static final Logger log = Logger.getLogger("GLOBAL");
	
	private Statement stmt = null;

	public void createAcct(Account acct){
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "INSERT INTO Account " + 
						 "VALUES('" + getIndex() + "', '" + acct.getBalance() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	
	public Account selectAcctById(Integer id){
		Account result = null;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM account " + 
						 "WHERE acctNumber = " + id;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int acctNum = 0;
			double balance = 0.0;
			while (rs.next()){
				acctNum = rs.getInt("ACCTNUMBER");
				balance = rs.getDouble("BALANCE");
			}
			result = new Account(acctNum, balance);	
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
		return result;
	};
	
	
	public void updateAcctBalance (int acctNum, double newAmt){
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "UPDATE Account " +
						 "SET balance = " + newAmt +
						 " WHERE acctNumber = " + acctNum;
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			System.out.println("Changed Balance. ");		
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	}
	
	public void displayAccts (){
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM account ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("| Account Number|  Balance  ");
			while (rs.next()){
				int acctNum = rs.getInt("ACCTNUMBER");
				double balance = rs.getDouble("BALANCE");
				System.out.println("|    " + acctNum + "    | $" + balance);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	}
	
	public int getIndex(){
		int result = 0;
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT COUNT(*) FROM account ";
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
	
	public ArrayList<Account> getAcctList(){
		ArrayList<Account> result = new ArrayList<Account>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM account ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				int acctNum = rs.getInt("ACCTNUMBER");
				double balance = rs.getDouble("BALANCE");
				result.add(new Account(acctNum, balance));
			}
		}catch(SQLException e){
			log.error("AccountDaoImpl: Error loading Records", e);
		}finally{
			close(stmt);
		}
		return result;
	}
	
	public void deleteAcct (Account acct){
		try(Connection conn = ConnectionUtil.getConnection();){	
			String sql = "DELETE FROM Account " + 
					     "WHERE acctNumber = " + acct;
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected + " Rows affected");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
}