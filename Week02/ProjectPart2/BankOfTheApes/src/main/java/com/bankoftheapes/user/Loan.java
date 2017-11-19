package com.bankoftheapes.user;

public class Loan {
	private double amount;
	private int approval;
	private String date;
	private String approvalDate;
	private String status;
	
	public Loan(double l, String date) {
		this.amount = l;
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getApproval() {
		return approval;
	}
	public void setApproval(int approval) {
		this.approval = approval;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Loan [amount=" + amount + ", approval=" + approval + "]";
	}
	
	
}
