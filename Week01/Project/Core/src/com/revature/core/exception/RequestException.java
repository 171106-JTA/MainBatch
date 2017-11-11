package com.revature.core.exception;

import com.revature.core.FieldParams;
import com.revature.core.Request;

public class RequestException extends Exception {
	private Request request;
	
	public RequestException(Request request) {
		super();
		this.request = request;
	}
	
	public RequestException(Request request, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.request = request;
	}
	
	public RequestException(Request request, String message, Throwable cause) {
		super(message, cause);
		this.request = request;
	}
	
	public RequestException(Request request, String message) {
		super(message);
		this.request = request;
	}
	
	public RequestException(Request request, Throwable cause) {
		super(cause);
		this.request = request;
	}
	
	public Request getRequest() {
		return request;
	}
}
