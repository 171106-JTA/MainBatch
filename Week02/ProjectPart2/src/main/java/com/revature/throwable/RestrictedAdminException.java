package com.revature.throwable;

import com.revature.model.Properties;

public class RestrictedAdminException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1058396532106181961L;

	public RestrictedAdminException(String token) {
		super(Properties.ERR_RESTRICTED + token);
		// TODO Auto-generated constructor stub
	}
}
