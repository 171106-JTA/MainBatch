package com.revature.throwable;

import com.revature.model.Properties;

public class IllegalAdminException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5474917500711418533L;

	public IllegalAdminException() {
		super(Properties.ERR_ILLEGAL_ADMIN);
	}
}