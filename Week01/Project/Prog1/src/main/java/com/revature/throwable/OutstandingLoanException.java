package com.revature.throwable;

import com.revature.model.Properties;

public class OutstandingLoanException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4625653155075985678L;

	public OutstandingLoanException() {
		super(Properties.ERR_EXCESS_LOAN);
	}
}
