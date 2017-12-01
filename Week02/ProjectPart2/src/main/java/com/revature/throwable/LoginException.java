package com.revature.throwable;

public abstract class LoginException extends BankingException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2239522949531913550L;

	public LoginException(String message) {
		super(message);
	}

}
