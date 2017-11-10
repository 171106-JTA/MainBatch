package com.revature.core.exceptions;

import com.revature.core.FieldParams;

public class RequestException extends Exception {
	private FieldParams request;
	private long userId;
	
	public RequestException(FieldParams request, long userId) {
		super();
		this.request = request;
		this.userId = userId;
	}
	
	public RequestException(FieldParams request, long userId, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.request = request;
		this.userId = userId;
	}
	public RequestException(FieldParams request, long userId, String message, Throwable cause) {
		super(message, cause);
		this.request = request;
		this.userId = userId;
	}
	public RequestException(FieldParams request, long userId, String message) {
		super(message);
		this.request = request;
		this.userId = userId;
	}
	public RequestException(FieldParams request, long userId, Throwable cause) {
		super(cause);
		this.request = request;
		this.userId = userId;
	}
	
	public FieldParams getRequest() {
		return request;
	}
	
	public long getUserId() {
		return userId;
	}
}
