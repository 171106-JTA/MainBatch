package com.revature.dao;

import java.util.ArrayList;

import com.revature.objects.Bank_account;;

public interface AccountDao {

	public void newAccount(Bank_account account);
	public Bank_account findById(String accountNber);
	public void updateBalance(String accountNber, double amount);
	public ArrayList<Bank_account> listOfAllAccounts();
	public int deleteAccount(String accountNber);
	public ArrayList<Bank_account> listOfAllAccounts(int customerId);
}
