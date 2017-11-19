package com.bankoftheapes.dao;

import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.User;

public interface BankDao {
	public BankAccount getAccountInfo(User user);
	public User getUserInfo(String username);
	public boolean userExists(String username);
	//public Loan getLoanInfo();
	public void showAllUsers();
	public void updateAccountAmount(BankAccount ba);
	//public void updateLoan();
	//public void updateAccessStatus();
	public void updateApproval(User u);
	//public void deleteLoan();
	public void addNewUser(User user);
	
}
