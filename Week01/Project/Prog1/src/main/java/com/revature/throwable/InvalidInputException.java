package com.revature.throwable;

import java.util.NoSuchElementException;

import com.revature.model.Properties;

public class InvalidInputException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893174906902063081L;

	public InvalidInputException(int select, int max) {
		super(Properties.ERR_INPUT + (max - 1) + ": " + select);
	}

	public InvalidInputException() {
		super(Properties.ERR_INPUT);
	}
}
