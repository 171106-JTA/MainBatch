package com.revature.throwable;

import com.revature.model.Properties;

public class NoSuchRequestException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8204079812450771958L;

	public NoSuchRequestException(int id) {
		super(Properties.ERR_REQ_DNE + id);
	}
}
