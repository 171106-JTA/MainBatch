package com.revature.beans;

public class ApprovalStatus {
	private int statusid;
	private String status;
	private int handlerid;
	public int getStatusid() {
		return statusid;
	}
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getHandlerid() {
		return handlerid;
	}
	public void setHandlerid(int handlerid) {
		this.handlerid = handlerid;
	}
	public String toJSON() {
		return "{"
				+ "'statusid': " + this.getStatusid()  + ","
				+ "'status': " + this.getStatus() + ","
				+ "'handlerid': " + this.getHandlerid()
				+ "}";
	}
}
