package com.revature.beans;

public class ReimbursementEvent {
	private  int eventid;
	private  String eventtype;
	private  float reimbursementpercent;
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
	public String getEventtype() {
		return eventtype;
	}
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	public float getReimbursementpercent() {
		return reimbursementpercent;
	}
	public void setReimbursementpercent(float reimbursementpercent) {
		this.reimbursementpercent = reimbursementpercent;
	}
	public String toJSON() {
		return "{" 
				+ "\"eventid\": " + this.getEventid() + ","
				+ "\"eventtype\": " + "\"" + this.getEventtype() + "\","
				+ "\"reimbursementpercent\": " + this.getReimbursementpercent()
				+ "}";
	}
}
