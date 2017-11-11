package com.revature.route;

import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.BankingProcessor;
import com.revature.processor.Processorable;
import com.revature.processor.UserProcessor;

public class RequestRouter implements Routeable {
	
	
	private static Processorable banking = new BankingProcessor();
	private static Processorable userProc = new UserProcessor();
	
	@Override
	public Resultset handleRequest(Request request) throws RequestException {
		switch (request.getRoute()) {
			case "BANKING": 
				return banking.process(request);
			case "USER":
				return userProc.process(request);
			default:
				throw new RequestException(request, "Route=[\'" + request.getRoute() + "\'] is unknown!");
		}
	}

}
