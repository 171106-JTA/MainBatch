package com.revature.beans;

public class ApprovalStatus {
	private int statusid;
	private int handlerposition;
	public int getStatusid() {
		return statusid;
	}
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}
	public int getHandlerposition() {
		return handlerposition;
	}
	public void setHandlerposition(int handlerposition) {
		this.handlerposition = handlerposition;
	}
	public String toJSON() {
		return "{"
				+ "\"statusid\": " + this.getStatusid()  + ","
				+ "\"handlerposition\": " + this.getHandlerposition()
				+ "}";
	}
}
