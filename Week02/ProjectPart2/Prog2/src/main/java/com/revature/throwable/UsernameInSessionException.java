package com.revature.throwable;

import com.revature.model.Properties;

public class UsernameInSessionException extends LoginException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5205283482780522031L;
	
	public UsernameInSessionException(String user) {
		super(Properties.ERR_USER_ONLINE + user);
	}

}
