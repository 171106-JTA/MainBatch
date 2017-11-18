package com.revature.dao;

import java.util.List;

import com.revature.BankAccount.User;

public interface UserDao {
	public boolean createUser(User user);
	public User getUser(String username, String password);
//	public List<String> getUsersConditionally(final int status, final int permissions);
}
