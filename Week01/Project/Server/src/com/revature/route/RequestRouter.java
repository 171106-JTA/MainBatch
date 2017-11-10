package com.revature.route;

import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.BankingProcessor;
import com.revature.processor.Processorable;
import com.revature.processor.UserProcessor;
import com.revature.processor.handler.*;

public class RequestRouter implements Routeable {
	
	
	private static Processorable banking = new BankingProcessor();
	private static Processorable userProc = new UserProcessor();
	
	@Override
	public Resultset handleRequest(FieldParams requestParams) throws RequestException {
		switch (requestParams.get("route")) {
			case "BANKING": 
				return banking.process(requestParams);
			case "USER":
				return userProc.process(requestParams);
			default:
				throw new RequestException(requestParams, 0, "Unknown route!");
		}
	}

}
