package com.revature.core.exception;

import com.revature.core.Request;

/**
 * Used when handling exceptions when processing user requests 
 * @author Antony Lulciuc
 */
public class RequestException extends Exception {
	private Request request;
	
	/**
	 * What caused the exception
	 * @param request - what caused the exception
	 */
	public RequestException(Request request) {
		super();
		this.request = request;
	}
	
	/**
	 * What caused the exception
	 * @param request - what caused the exception
	 * @param message - meta data on exception
	 * @param cause - was there a cause
	 * @param enableSuppression - see parent class
	 * @param writableStackTrace - see parent class
	 */
	public RequestException(Request request, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.request = request;
	}
	
	/**
	 * What caused the exception
	 * @param request - what caused the exception
	 * @param message - meta data on exception
	 * @param cause - was there a cause
	 */
	public RequestException(Request request, String message, Throwable cause) {
		super(message, cause);
		this.request = request;
	}
	
	/**
	 * What caused the exception
	 * @param request - what caused the exception
	 * @param message - meta data on exception
	 */
	public RequestException(Request request, String message) {
		super(message);
		this.request = request;
	}
	
	/**
	 * What caused the exception
	 * @param request - what caused the exception
	 * @param cause - was there a cause
	 */
	public RequestException(Request request, Throwable cause) {
		super(cause);
		this.request = request;
	}
	
	/**
	 * @return What caused the exception
	 */
	public Request getRequest() {
		return request;
	}
}
