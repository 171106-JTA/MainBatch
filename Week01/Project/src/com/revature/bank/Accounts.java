package com.revature.bank;

import java.util.LinkedList;

public class Accounts extends LinkedList<Account> {
	private static final long serialVersionUID = 7L;
	
	/**
	 * Fetches an Account from this List of accounts, by ID. Think of "SELECT * FROM Accounts WHERE AccountID = ..."
	 * @param id
	 * @return the Account that has this id, or null if one cannot be found
	 */
	public Account getAccountByID(int id)
	{
		for (Account acct : this)
		{
			if (acct.getNumber() == id) return acct;
		}
		return null;
	}
	
}
