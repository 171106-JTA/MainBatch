package com.revature.dao;

import java.util.List;

import com.revature.BankAccount.User;

public interface UserDao {
	public boolean createUser(User user);
	public User getUserCheckPassword(String username, String password);
	public User getUser(String username);
	public List<String> getUsersConditionally(final int status, final int permissions);
	public boolean alterUserStatusAndPermission(String username, final int currentStatus, final int currentPermission, 
			final int newStatus, final int newPermission);
	public boolean alterAccountAmount(User user);
}
