package com.revature.processor;

import javax.management.modelmbean.RequiredModelMBean;

import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.core.request.Request;
import com.revature.processor.handler.UserRequestHandler;
import com.revature.server.Server;

/**
 * For Basic data queries 
 * @author Antony Lulciuc
 *
 */
public class UserProcessor implements Processorable {
	private static UserRequestHandler URH = new UserRequestHandler();
	
	@Override
	public Resultset process(FieldParams requestParams) throws RequestException {
		Resultset resultset = null;
		
		switch (requestParams.get("transtype").toUpperCase()) {
			case "LOGIN":
				Request.require(new String[]{"username","password"}, requestParams);
				resultset = URH.login(requestParams);
				break;
			case "GETUSER":
				break;
			case "SETUSER":
				break;
			case "GETUSERINFO":
				break;
			case "SETUSERINFO":
				break;
			case "GETACCOUNT":
				break;
			case "SETACCOUNT":
				break;
			case "GETCHECKINGACCOUNT":
				break;
			case "SETCHECKINGACCOUNT":
				break;
			case "GETCEDITACCOUNT":
				break;
			case "SETCREDITACCOUNT":
				break;
			default:
				throw new RequestException(requestParams, Long.parseLong(requestParams.get("userid")), "Transtype=[\'" + requestParams.get("transtype").toUpperCase() + "\'] is unknown!");
		}
		
		return resultset;
	}
}
