package com.revature.throwable;

import com.revature.model.Properties;

public class InvalidUserException extends RegistrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304416650431845997L;

	public InvalidUserException(int user) {
		super(Properties.ERR_USER + ": " + user + "\n" + Properties.USER_CREDENTIALS);
	}
}