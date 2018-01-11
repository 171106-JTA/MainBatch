package com.revature.throwable;

import com.revature.model.Properties;

public class DeserializationError extends FatalError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 191744150748167382L;
	
	public DeserializationError(Exception e) {
		super(Properties.ERR_DESER, e);
	}

	public DeserializationError() {
		super(Properties.ERR_DESER);
	}

}