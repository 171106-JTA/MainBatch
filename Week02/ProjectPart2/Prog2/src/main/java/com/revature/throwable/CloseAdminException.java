package com.revature.throwable;

import com.revature.model.Properties;

public class CloseAdminException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6901424047837808779L;

	public CloseAdminException() {
		super(Properties.ERR_ADMIN_CLOSE);
	}
}
