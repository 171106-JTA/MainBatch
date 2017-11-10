package com.revature.bankproject;

public abstract class Bank {
	
    protected double balance = 0;
	protected int totalDeposits;
	protected int totalWithdraws;
	
	public Bank() {}
	
	public Bank(double b) {
		if (balance >= 0) {
			balance = b;
		}
		else {
			balance = 0;
		}
	}
	/*
	public Bank(double balance, int totalDeposits, int totalWithdraws) {
		super();
		this.balance = balance;
		this.totalDeposits = totalDeposits;
		this.totalWithdraws = totalWithdraws;
	}*/
	
	public abstract void deposit(double d);
	public abstract void withdraw(double w);
	
	public abstract double getBalance();
	

}
