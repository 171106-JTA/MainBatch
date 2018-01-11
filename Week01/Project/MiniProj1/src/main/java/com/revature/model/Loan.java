package com.revature.model;

public class Loan {
	/**
	 * A Loan to issue to a user account
	 */
	private double amount;
	private int uid;
	private static int id = 0;
	
	public Loan(double amount) {
		this.amount = amount;
		synchronized(Loan.class) {
			uid = id;
			id++;
		}
	}
	
	/**
	 * Pay down the amount of a loan. If the loan is completely paid off, it 
	 * will be removed from the user list
	 */
	public boolean payDownLoan(double amount) {
		this.amount -= amount;
		return this.amount <= 0.0;
	}
	
	public int getUid() {
		return uid;
	}
	
	public String toString() {
		return "Loan " + uid + ": $" + amount;
	}
}
