package com.revature.dao;

import com.revature.BankAccount.User;

public interface UserDao {
	public void createUser(User user);
	public User getUser(String username, String password);
}
