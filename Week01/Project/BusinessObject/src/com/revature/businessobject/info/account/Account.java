package com.revature.businessobject.info.account;

import com.revature.businessobject.info.Info;

public class Account extends Info {
	private long number;
	private AccountType type;

	/**
	 * Initialize basic account data 
	 * @param userId user unique identifier (primary key)
	 * @param number account number 
	 * @param type what kind of account is it
	 * @see AccountType 
	 */
	public Account(long userId, long number, AccountType type) {
		super(userId);
		this.number = number;
		this.type = type;
	}

	public long getNumber() {
		return number;
	}

	public AccountType getType() {
		return type;
	}
}
