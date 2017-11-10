package com.revature.bankproject;

public class Account implements Bank, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double balance;

	public Account() {super();}
	
	public Account(double b) {
		super();
	}


	public void deposit(double d) {
		balance += d;
	}

	public void withdraw(double w) {
		if (w > balance) {
			System.out.println("You don't have sufficent funds.");
		}
		else {
			balance -= w;
		}
	}

	public double getBalance() {
		return balance;
		
	}
	
	public void setBalance(double balance) {
		this.balance = balance;	
	}

}
