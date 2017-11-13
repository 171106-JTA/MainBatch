package com.revature.throwable;

import com.revature.model.Properties;

public class InvalidLoginException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8271977479158360618L;

	public InvalidLoginException(String user, String pass) {
		super(Properties.ERR_USER + user + ": " + pass);
	}
}
