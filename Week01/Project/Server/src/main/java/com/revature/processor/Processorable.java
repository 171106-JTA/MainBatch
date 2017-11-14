package com.revature.processor;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

/**
 * Interprets request transtype to perform desired action 
 * @author Antony Lulciuc
 */
public interface Processorable {
	/**
	 * Process user request 
	 * @param request - what to process
	 * @return request of execution
	 * @throws RequestException
	 */
	public Resultset process(Request request) throws RequestException;
}

