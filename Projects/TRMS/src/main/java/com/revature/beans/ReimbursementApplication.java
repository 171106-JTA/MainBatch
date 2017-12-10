package com.revature.beans;

public class ReimbursementApplication {
	private int applicationid;
	private int employeeid;
	private int statusid;
	private int eventid;
	private float amount;
	private int ispassed;
	private int isapproved;
	public int getApplicationid() {
		return applicationid;
	}
	public void setApplicationid(int applicationid) {
		this.applicationid = applicationid;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public int getStatusid() {
		return statusid;
	}
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int isPassed() {
		return ispassed;
	}
	public void setPassed(int ispassed) {
		this.ispassed = ispassed;
	}
	public int isApproved() {
		return isapproved;
	}
	public void setApproved(int isapproved) {
		this.isapproved = isapproved;
	}
	public String toJSON() {
		return "{"
				+ "\"applicationid\": " + this.getApplicationid() + ","
				+ "\"employeeid\": " + this.getEmployeeid() + ","
				+ "\"statusid\": " + this.getStatusid() + ","
				+ "\"eventid\": " + this.getEventid() + ","
				+ "\"amount\": " + this.getAmount() + ","
				+ "\"ispassed\": " + this.isPassed() + ","
				+ "\"isapproved\": " + this.isApproved()
				+ "}";
	}
}
