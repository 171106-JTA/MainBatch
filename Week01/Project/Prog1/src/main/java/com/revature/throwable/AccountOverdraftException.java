package com.revature.throwable;

import com.revature.model.Properties;

public class AccountOverdraftException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6157819630195973773L;
	
	public AccountOverdraftException() {
		super(Properties.ERR_ACCT_OVERDRAFT + "\n" +
				Properties.ERR_ACCT_OVERDRAFT_PENALTY);
	}
}
