package com.revature.dao;

import com.revature.BankAccount.User;

public interface UserDao {
	public void createUser(User user);
	public User checkIfUserExists(String username, String password);
	
	
}
