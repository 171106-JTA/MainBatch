package com.revature.throwable;

public abstract class RegistrationException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8091377064449195763L;

	public RegistrationException(String message) {
		super(message);
		
	}

}