package com.revature.throwable;

import com.revature.model.Properties;

public class LogoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2628788685550598732L;
	
	public LogoutException() {
		super(Properties.LOGOUT_STR);
	}

}
