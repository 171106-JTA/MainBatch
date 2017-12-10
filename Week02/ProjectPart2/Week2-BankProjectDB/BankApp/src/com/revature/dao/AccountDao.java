package com.revature.dao;

import com.revature.beans.Account;

public interface AccountDao {

	public void createAcct(Account acct);
	public Account selectAcctById(Integer id);
	public void updateAcctBalance (int acctNum, double newAmt);
	public void displayAccts();
	
}