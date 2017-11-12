package com.revature.core;

import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;

public class Request {
	private int sessionId;
	private long userId;
	private Checkpoint checkpoint;
	private String route;
	private String transtype;
	private FieldParams query;
	private FieldParams transaction;
	
	public Request(FieldParams loginResponse, String route, String transtype, FieldParams query, FieldParams transaction) {
		super();
		this.sessionId = loginResponse.get(User.SESSIONID) == null ? 0 : Integer.parseInt(loginResponse.get(User.SESSIONID));
		this.userId = loginResponse.get(User.ID) == null ? 0 : Long.parseLong(loginResponse.get(User.ID));
		this.route = route.toUpperCase();
		this.transtype = transtype.toUpperCase();
		this.query = query;
		this.transaction = transaction;
		this.checkpoint = Checkpoint.NONE;
	}
	
	public Request(int sessionId, long userId, String route, String transtype,
			FieldParams query, FieldParams transaction) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.route = route.toUpperCase();
		this.transtype = transtype.toUpperCase();
		this.query = query;
		this.transaction = transaction;
		this.checkpoint = Checkpoint.NONE;
	}
	
	public int getSessionId() {
		return sessionId;
	}

	public long getUserId() {
		return userId;
	}

	public Checkpoint getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(Checkpoint checkpoint) {
		this.checkpoint = checkpoint;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public FieldParams getQuery() {
		return query;
	}

	public void setQuery(FieldParams query) {
		this.query = query;
	}

	public FieldParams getTransaction() {
		return transaction;
	}

	public void setTransaction(FieldParams transaction) {
		this.transaction = transaction;
	}


}
