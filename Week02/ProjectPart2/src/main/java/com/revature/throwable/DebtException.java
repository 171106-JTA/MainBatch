package com.revature.throwable;

import com.revature.model.Properties;

public class DebtException extends BankingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5457760132775518455L;
	
	public DebtException() {
		super(Properties.ERR_LOAN_DEBT);
	}
}
