package com.bankoftheapes.dao;

import com.bankoftheapes.user.Loan;
import com.bankoftheapes.user.User;

public interface BankDao {
	//public Account getAccountInfo();
	public User getUserInfo(String username);
	public boolean userExists(String username);
	//public Loan getLoanInfo();
	//public void showAllUsers();
	//public void updateAccountAmount();
	//public void updateLoan();
	//public void updateAccessStatus();
	//public void deleteLoan();
	//public void uploadUser();
	
}
