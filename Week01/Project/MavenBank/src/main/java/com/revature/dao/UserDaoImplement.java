package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.revature.BankAccount.User;
import com.revature.util.ConnectionUtil;

public class UserDaoImplement implements UserDao {
	CallableStatement cs = null;
	public void createUser(User user) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String 	username = user.getUsername(), 
					firstname = user.getFirstName(),
					lastname = user.getLastName(), 
					middleInitial = user.getMiddleInitial(),
					password = user.getPassword();
			int 	status = user.getStatus(),
					permission = user.getPermissions();
			double 	accountAmount = user.getAccountAmount();
			String sql = "{call insert_user(?,?,?,?,?,?,?,?)";
			
			//Store data in list and user a loop here
			cs = conn.prepareCall(sql);
			cs.setString(1, username);
			cs.setString(2, firstname);
			cs.setString(3, lastname);
			cs.setString(4, middleInitial);
			cs.setString(5, password);
			cs.setInt(6, status);
			cs.setInt(7, permission);
			cs.setDouble(8, accountAmount);
			
			cs.executeQuery();
			System.out.println();
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
