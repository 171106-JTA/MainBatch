package com.bankoftheapes.dao;

import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.User;

public interface BankDao {
	public BankAccount getAccountInfo(User user);
	public User getUserInfo(String username);
	public boolean userExists(String username);
	public void showLoans(User user);
	public void showAllUsers();
	public void updateAccountAmount(BankAccount ba);
	public void updateAccessStatus(User u);
	public void updateApproval(User u);
	public void addNewUser(User user);
	public void applyLoan(User user);
}
