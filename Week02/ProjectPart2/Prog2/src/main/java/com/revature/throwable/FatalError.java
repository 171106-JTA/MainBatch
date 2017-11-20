package com.revature.throwable;

public class FatalError extends Throwable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2247507159742252268L;

	public FatalError(String err) {
		super(err);
	}

	public FatalError(String err, Exception e) {
		super(err, e);
	}
}
