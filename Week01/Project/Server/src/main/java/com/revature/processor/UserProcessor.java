package com.revature.processor;

import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Type;
import com.revature.businessobject.info.user.UserInfo;
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
 */
public class UserProcessor implements Processorable {
	private static UserRequestHandler URH = new UserRequestHandler();
	
	/**
	 * Process user request 
	 * @param request - what to process
	 * @return request of execution
	 * @throws RequestException
	 */
	@Override
	public Resultset process(Request request) throws RequestException {
		Resultset res = null;
		
		switch (request.getTranstype()) {
			case "LOGIN":
				Require.requireAllQuery(new String[]{ User.USERNAME, User.PASSWORD }, request);
				res = URH.login(request);
				break;
			case "CREATEUSER":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.NONE }, request);
				Require.requireAllTransaction(new String[] { User.USERNAME,  User.PASSWORD }, request);
				res = URH.createUser(request);
				break;
			case "DELETEUSER":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER, Checkpoint.PENDING, Checkpoint.NONE }, request);
				Require.requireAllQuery(new String[] { User.USERNAME,  User.PASSWORD }, request);
				res = URH.deleteUser(request);
				break;
			case "GETUSER":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER }, request);
				res = URH.getUser(request);
				break;
			case "SETUSER":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				Require.requireAllQuery(new String[] { User.USERNAME, User.PASSWORD }, request);
				Require.requireTransaction(new String[] { User.USERNAME, User.PASSWORD, User.CHECKPOINT }, request);
				res = URH.setUser(request);
				break;
			case "CREATEUSERINFO":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.PENDING, Checkpoint.NONE }, request);
				Require.requireAllTransaction(new String[] { UserInfo.USERID, UserInfo.PHONENUMBER, UserInfo.EMAIL, UserInfo.ADDRESS1 }, request);
				res = URH.createUserInfo(request);
				break;
			case "GETUSERINFO":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER, Checkpoint.PENDING },  request);
				res = URH.getUserInfo(request);
				break;
			case "SETUSERINFO":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				Require.requireAllTransaction(new String[] { UserInfo.ADDRESS1, UserInfo.EMAIL, UserInfo.PHONENUMBER },  request);
				res = URH.setUserInfo(request);
				break;
			case "CREATECHECKINGACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.CUSTOMER },  request);
				res = URH.createAccount(request, Type.CHECKING);
				break;
			case "CREATECREDITACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.CUSTOMER },  request);
				res = URH.createAccount(request, Type.CREDIT);
				break;
			case "DELETEACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.deleteAccount(request);
				break;
			case "GETACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.getAccount(request);
				break;
			case "GETCHECKINGACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER },  request);
				res = URH.getAccount(request, Type.CHECKING);
				break;
			case "GETCEDITACCOUNT":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER},  request);
				res = URH.getAccount(request, Type.CREDIT);
				break;
			case "SETACCOUNTSTATUS":
				Require.requireCheckpoint(new String[] { Checkpoint.ADMIN },  request);
				Require.requireQuery(new String[] { Info.USERID }, request);
				Require.requireTransaction(new String[] { Account.STATUSID }, request);
				res = URH.setAccountStatus(request);
				break;
			default:
				throw new RequestException(request, "Transtype=[\'" + request.getRoute() + "."+ request.getTranstype() + "\'] is unknown!");
		}
		
		return res;
	}
}
