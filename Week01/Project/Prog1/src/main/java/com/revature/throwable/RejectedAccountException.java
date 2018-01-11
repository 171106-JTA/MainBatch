package com.revature.throwable;

import com.revature.model.Properties;

public class RejectedAccountException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3105793979253085620L;

	public RejectedAccountException(String user) {
		super(user + ": " + Properties.ACCT_REJECTED + "\n" + Properties.ADMIN_PHONE);
	}

}