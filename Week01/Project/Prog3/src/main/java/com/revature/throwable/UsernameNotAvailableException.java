package com.revature.throwable;

import com.revature.model.Properties;

public class UsernameNotAvailableException extends RegistrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 819676124935441797L;
	
	public UsernameNotAvailableException(String user) {
		super(Properties.ERR_USER_NA + ": " + user);
	}
}
