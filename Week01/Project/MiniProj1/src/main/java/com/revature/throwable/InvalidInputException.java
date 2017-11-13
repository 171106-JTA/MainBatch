package com.revature.throwable;

import java.util.NoSuchElementException;

import com.revature.model.Properties;

public class InvalidInputException extends NoSuchElementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893174906902063081L;

	public InvalidInputException(int input, int max) {
		super(Properties.ERR_INPUT + (max - 1) + ": " + input);
	}

	public InvalidInputException() {
		super(Properties.ERR_INPUT);
	}
}
