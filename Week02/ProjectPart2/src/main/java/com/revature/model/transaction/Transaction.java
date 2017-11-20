package com.revature.model.transaction;

import java.util.Date;

import com.revature.model.account.User;

public class Transaction extends User {
	private double amount;
	private int transactionID;
	private Date transactionDate;
	private TransType transactionType;
	
	@Override
	public String toString() {
		return "Transaction [\"transactionID=" + transactionID + ", transactionDate="
				+ transactionDate + ", transactionType=" + transactionType + " amount=" + amount +"]";
	}

	public Transaction() {
		setAmount(0);
	}

	// getters and setters
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public TransType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransType transactionType) {
		this.transactionType = transactionType;
	}
}
