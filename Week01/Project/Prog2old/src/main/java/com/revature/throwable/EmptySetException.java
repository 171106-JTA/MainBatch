package com.revature.throwable;

import com.revature.model.Properties;

public class EmptySetException extends BankingException {

	public EmptySetException() {
		super(Properties.EMPTY_LIST);
	}	
}
