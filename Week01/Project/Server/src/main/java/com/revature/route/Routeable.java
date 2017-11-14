package com.revature.route;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

/**
 * Routes request to appropriate sub-system
 * @author Antony Lulciuc
 */
public interface Routeable {
	/**
	 * Used to split request into appropriate sub-system
	 * @param request - from client
	 * @return result of processed request 
	 * @throws RequestException
	 */
	public Resultset handleRequest(Request request) throws RequestException;
}
