package com.revature.businessobject.info.account;

import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.AccountType;

public class Credit extends Account {
	private float total;
	private float interest;
	private float creditLimit;
	
	/**
	 * Initializes Credit account
	 * @param userId unique identifier (primary key)
	 * @param number account
	 * @param total amount owed 
	 * @param interest 
	 * @param creditLimit
	 */
	public Credit(long userId, long number, float total, float interest, float creditLimit) {
		super(userId, number, AccountType.CREDIT);
		this.total = total;
		this.interest = interest;
		this.creditLimit = creditLimit;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}


	public float getInterest() {
		return interest;
	}


	public void setInterest(float interest) {
		this.interest = interest;
	}


	public float getCreditLimit() {
		return creditLimit;
	}


	public void setCreditLimit(float creditLimit) {
		this.creditLimit = creditLimit;
	}
}
