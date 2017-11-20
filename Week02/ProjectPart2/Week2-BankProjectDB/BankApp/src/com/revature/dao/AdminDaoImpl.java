package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Administrator;
import com.revature.util.ConnectionUtil;

public class AdminDaoImpl implements AdminDao {
	
	private static final Logger log = Logger.getLogger("GLOBAL");
	
	private Statement stmt = null;
	
	public void createAdmin(Administrator admin){
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "INSERT INTO Administrator " + 
						 "VALUES('"+admin.getUsername()+ 
						 "', '" + admin.getPassword() + "')";
			stmt = conn.createStatement();
			int affected = stmt.executeUpdate(sql);
			
			System.out.println(affected + " Rows affected");
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}
	};
	
	
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
	
	public ArrayList<Administrator> getAdminList(){
		ArrayList<Administrator> result = new ArrayList<Administrator>();
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "SELECT * " +
						 "FROM administrator ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				String adminName = rs.getString("ADMINNAME");
				String password = rs.getString("PASSWORD");
				result.add(new Administrator(adminName, password));
			}
		}catch(SQLException e){
			log.error("AdminDaoImpl: Error loading Records", e);
		}finally{
			close(stmt);
		}
		return result;
	}

}

