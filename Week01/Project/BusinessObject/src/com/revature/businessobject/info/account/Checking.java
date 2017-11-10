package com.revature.businessobject.info.account;

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
