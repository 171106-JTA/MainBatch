package com.revature.model.request;

import java.io.Serializable;

public abstract class Request implements Serializable {
	private int uid;
	private static int id = 0;
	private String user;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3113170732871070117L;
	
	public Request(String name) {
		super();
		this.user = name;
		synchronized(Request.class) {
			uid = id;
			id++;
		}
	}
	
	public int getUid() {
		return uid;
	}
	
	public String getUser() {
		return user;
	}

	@Override
	public abstract String toString();
}
