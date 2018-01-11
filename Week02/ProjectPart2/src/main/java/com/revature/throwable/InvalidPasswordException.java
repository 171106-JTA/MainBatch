package com.revature.throwable;

import com.revature.model.Properties;

public class InvalidPasswordException extends RegistrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8986922400538922750L;
	public InvalidPasswordException(String pass) {
		super(Properties.ERR_PASS + ": " + pass + "\n" + Properties.PASS_CREDENTIALS);
	}
}
