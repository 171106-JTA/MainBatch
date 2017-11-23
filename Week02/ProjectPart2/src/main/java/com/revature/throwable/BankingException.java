package com.revature.throwable;

public abstract class BankingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5025443244836788468L;

	public BankingException(String message) {
		super(message);
	}
}
