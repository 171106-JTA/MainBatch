package com.revature.throwable;

import com.revature.model.Properties;

public class DuplicateRequestException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4998525417884602758L;
	public DuplicateRequestException(int i) {
		super(Properties.ERR_REQ_DUP + i);
	}

}
