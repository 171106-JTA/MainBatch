package com.revature.throwable;

import com.revature.model.Properties;

public class NoLineError extends Throwable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8536909346843505026L;

	public NoLineError(Exception e) {
		super(Properties.ERR_NO_LINE, e);
	}

}
