package com.revature.throwable;

import com.revature.model.Properties;

public class DuplicateAdminException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1962228375834981167L;

	public DuplicateAdminException(String token) {
		super(Properties.ERR_ADMIN_DUP + token);
	}
}