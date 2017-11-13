package com.revature.users;

import java.io.Serializable;

public class Loan implements Serializable{
	private double amount;
	private boolean approval;
	
	public Loan(double l) {
		this.amount = l;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	@Override
	public String toString() {
		return "Loan [amount=" + amount + ", approval=" + approval + "]";
	}
	
	
}