package com.revature.throwable;

import com.revature.model.Properties;

public class InvalidUserNameException extends RegistrationException {

	public InvalidUserNameException(String message) {
		super(Properties.ERR_USER + ": " + message + " " + Properties.USER_CREDENTIALS);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7007199266842093739L;

}
