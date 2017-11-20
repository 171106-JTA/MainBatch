package com.bankoftheapes.user;

public class BankAccount {
	private double amount;
	private int accId;
	
	public BankAccount(double amount, int accId) {
		super();
		this.amount = amount;
		this.accId = accId;
	}
	
	public BankAccount(int accId) {
		this.accId = accId;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	
	@Override
	public String toString() {
		return "Account [amount=" + amount + ", AccId=" + accId + "]";
	}
	
	
	
}
