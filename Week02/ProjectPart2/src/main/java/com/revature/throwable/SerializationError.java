package com.revature.throwable;

import com.revature.model.Properties;

public class SerializationError extends FatalError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2719768574153157941L;
	
	public SerializationError(Exception e) {
		super(Properties.ERR_SER, e);
	}

}
