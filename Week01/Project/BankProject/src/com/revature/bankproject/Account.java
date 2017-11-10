package com.revature.bankproject;

public class Account extends Bank implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Account() {
		super();
	}
	
	public Account(double balance) {
		super(balance);
	}


	@Override
	public void deposit(double d) {
		balance += d;
	}

	@Override
	public void withdraw(double w) {
		if (w > balance) {
			System.out.println("You don't have sufficent funds.");
		}
		else {
			balance -= w;
		}
	}

	@Override
	public double getBalance() {
		return balance;
	}

}
