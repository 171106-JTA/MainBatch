package com.revature.model.interfaces.dao;

import java.util.*;

import com.revature.model.account.UserAccount;

public interface UserDao {

	public Map<String, UserAccount> getUsers();
	
	public Set<Set<String>> getCreds(boolean atomic);
	
	public UserAccount fetchUser(boolean atomic);
	
	public boolean addUser(boolean atomic);
	
	public boolean updateUser(boolean atomic);
	
	public boolean deleteUser(boolean atomic);
}
