package com.revature.model.request;

public class LoanRequest extends Request {
	private double amount;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7357295452808985446L;

	public LoanRequest(String user, double amount) {
		super(user);
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "LoanRequest [amount=" + amount + ", uid=" + getUid() + ", user=" + getUser() + "]";
	}
}
