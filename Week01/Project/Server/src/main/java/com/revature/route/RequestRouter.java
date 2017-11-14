package com.revature.route;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.BankingProcessor;
import com.revature.processor.Processorable;
import com.revature.processor.UserProcessor;

/**
 * Routes request to appropriate sub-system
 * @author Antony Lulciuc
 */
public class RequestRouter implements Routeable {
	private static Processorable banking = new BankingProcessor();
	private static Processorable userProc = new UserProcessor();
	
	/**
	 * Used to split request into appropriate sub-system
	 * @param request - from client
	 * @return result of processed request 
	 * @throws RequestException
	 */
	@Override
	public Resultset handleRequest(Request request) throws RequestException {
		switch (request.getRoute()) {
			case Routes.BANKING: 
				return banking.process(request);
			case Routes.USER:
				return userProc.process(request);
			default:
				throw new RequestException(request, "Route=[\'" + request.getRoute() + "\'] is unknown!");
		}
	}

}
