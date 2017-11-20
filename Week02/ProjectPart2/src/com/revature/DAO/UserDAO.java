package com.revature.DAO;

import com.revature.bank.Accounts;
import com.revature.bank.User;
import com.revature.bank.Users;

public interface UserDAO {
	public User authenticate(User user);
	// a method dealing with an individual User per each CRUD operation
	public void create(User user);
	public User getUserById(Integer id);
	public int update(User user);
	public int delete(User user);
	// method for getting the User by their username
	public User findUserByUsername(String username);
	// user state modification methods
	public int activate(User user);
	public int lock(User user);
	public int flag(User user);
	public int ban(User user);
	// methods for getting Users (as a collection)
	public Users getAllUsers();
	public Users getAllBaseUsers();
	public Users getUsersByState(String state);
	// methods for getting Accounts (These probably belong in the AccountsDAO
	public Accounts getAccountsFor(User user);
	public Accounts getAccountsForUserId(Integer id);
}
