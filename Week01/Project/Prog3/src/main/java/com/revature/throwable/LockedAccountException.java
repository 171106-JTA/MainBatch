package com.revature.throwable;

import com.revature.model.Properties;

public class LockedAccountException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1417690986722248262L;
	
	public LockedAccountException(String user) {
		super(user + ": " + Properties.ACCT_LOCKED + "\n" + Properties.ADMIN_PHONE);
	}

}
