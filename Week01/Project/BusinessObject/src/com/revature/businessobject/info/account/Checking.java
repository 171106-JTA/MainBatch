package com.revature.businessobject.info.account;

import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.AccountType;

public class Checking extends Account {
	private float total;
	
	public Checking(long userId, long number, float total) {
		super(userId, number, AccountType.CHECKING);
		this.total = total;
	}
	
	public float getTotal() {
		return total;
	}
}
