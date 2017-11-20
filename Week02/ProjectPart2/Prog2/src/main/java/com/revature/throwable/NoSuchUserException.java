package com.revature.throwable;

import java.util.NoSuchElementException;

import com.revature.model.Properties;

public class NoSuchUserException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7705869380660514416L;

	public NoSuchUserException(String string) {
		super(Properties.ERR_ACCT_DNE + string);
	}
}
