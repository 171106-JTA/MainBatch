package com.revature.throwable;

public abstract class BadRequestException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5633050745676595536L;

	public BadRequestException(String string) {
		super(string);
	}
	
}
