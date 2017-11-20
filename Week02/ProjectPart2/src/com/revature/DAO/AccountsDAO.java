package com.revature.DAO;

import com.revature.bank.Account;
import com.revature.bank.User;

public interface AccountsDAO {
	// CRUD methods for interacting with single Accounts here
	public void createAccountForUser(User user);
	public void createAccountForUser(User user, Account acct);
	public Account getAccountForUser(User user);
	public int updateAccountForUser(User user, Account acct);
	public int deleteAccountForUser(User user);
	// methods for adding Users to account
}
