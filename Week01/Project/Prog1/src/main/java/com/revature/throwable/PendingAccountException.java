package com.revature.throwable;

import com.revature.model.Properties;

public class PendingAccountException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1953815963890862337L;

	public PendingAccountException(String user) {
		super(user + ": " + Properties.ACCT_PENDING);
	}
}
