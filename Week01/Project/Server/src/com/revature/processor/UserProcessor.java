package com.revature.processor;

import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.handler.UserRequestHandler;
import com.revature.server.session.require.Require;

/**
 * For Basic data queries 
 * @author Antony Lulciuc
 *
 */
public class UserProcessor implements Processorable {
	private static UserRequestHandler URH = new UserRequestHandler();
	
	@Override
	public Resultset process(Request request) throws RequestException {
		Resultset res = null;
		
		switch (request.getTranstype()) {
			case "LOGIN":
				Require.requireAllQuery(new String[]{ User.USERNAME, User.PASSWORD }, request);
				res = URH.login(request);
				break;
			case "CREATEUSER":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER }, request);
				res = URH.createUser(request);
				break;
			case "GETUSER":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER }, request);
				res = URH.getUser(request);
				break;
			case "SETUSER":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.setUser(request);
				break;
			case "GETUSERINFO":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.getUserInfo(request);
				break;
			case "SETUSERINFO":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				break;
			case "GETACCOUNT":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.getAccount(request);
				break;
			case "GETCHECKINGACCOUNT":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				break;
			case "SETCHECKINGACCOUNT":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				break;
			case "GETCEDITACCOUNT":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER},  request);
				break;
			case "SETCREDITACCOUNT":
				Require.require(new Checkpoint[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				break;
			default:
				throw new RequestException(request, "Transtype=[\'" + request.getTranstype() + "\'] is unknown!");
		}
		
		return res;
	}
}
