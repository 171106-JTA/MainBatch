package com.revature.model.transaction;

public class Transaction {
	private double amount;
	private static int transactionID;
	
	Transaction(){
		setAmount(0);
	}
	
	// getters and setters
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public static int getTransactionID() {
		return transactionID;
	}
	public static void setTransactionID(int transactionID) {
		Transaction.transactionID = transactionID;
	}
}
