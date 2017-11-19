package com.revature.model.transaction;

public class Transaction {
	private double amount;
	Transaction(){
		setAmount(0);
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
