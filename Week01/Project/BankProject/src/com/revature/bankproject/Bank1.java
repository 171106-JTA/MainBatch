/*package com.revature.bankproject;

public abstract class Bank1 {
	
    protected double balance;
	protected int totalDeposits;
	protected int totalWithdraws;
	
	public Bank1() {}
	
	public Bank1(double b) {
		if (balance > 0) {
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
	}
	
	public abstract void deposit(double d);
	public abstract void withdraw(double w);
	
	public abstract double getBalance();
	
	
	
	
	package com.revature.bankproject;

public class Account extends Bank implements java.io.Serializable {



	private static final long serialVersionUID = 1L;
	
	public Account() {super();}
	
	public Account(double b) {
		super(b);
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
	
	public void setBalance(double balance) {
		this.balance = balance;	
	}

}

	

}


*/