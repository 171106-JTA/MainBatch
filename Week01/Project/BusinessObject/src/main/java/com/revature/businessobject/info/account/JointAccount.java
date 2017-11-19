package com.revature.businessobject.info.account;

import com.revature.businessobject.BusinessObject;

public class JointAccount extends BusinessObject {
	
	
	private long userIdOne;
	private long userIdTwo;
	private String accountNumber;
	
	public JointAccount() {
		super();
	}
	
	public JointAccount(long userIdOne, long userIdTwo, String accountNumber) {
		super();
		this.userIdOne = userIdOne;
		this.userIdTwo = userIdTwo;
		this.accountNumber = accountNumber;
	}

	public long getUserIdOne() {
		return userIdOne;
	}

	public void setUserIdOne(long userIdOne) {
		this.userIdOne = userIdOne;
	}

	public long getUserIdTwo() {
		return userIdTwo;
	}

	public void setUserIdTwo(long userIdTwo) {
		this.userIdTwo = userIdTwo;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}	
}
