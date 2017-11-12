package com.revature.processor.handler;

import java.util.Arrays;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountStatus;
import com.revature.businessobject.info.account.AccountType;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.processor.handler.helper.GenericHelper;
import com.revature.server.Server;
import com.revature.server.session.require.Require;

public final class UserRequestHandler {
	
	/**
	 * Performs select query on database for user with specified username and password
	 * @param request request with username and password
	 * @return User BusinessObject if found else empty resultset 
	 */
	public Resultset login(Request request) throws RequestException {
		Resultset res = Server.database.select(BusinessClass.USER, request.getQuery());
		
		if (res.size() > 0) {
			User user = (User) res.get(0);
			Require.requireCheckpoint(new String[] { Checkpoint.ADMIN, Checkpoint.CUSTOMER, 
					Checkpoint.PENDING, Checkpoint.NONE }, user.getCheckpoint(), request);
		}
		
		return res;
	}
	
	///
	//	USER
	///
	
	/**
	 * Creates new user accounts 
	 * @param request account information
	 * @return modified record count should equal 1
	 */
	public Resultset createUser(Request request) throws RequestException {
		BusinessObject user = GenericHelper.getLargest(BusinessClass.USER, Arrays.asList(new String[] { User.ID }));
		FieldParams transact = request.getTransaction();
		FieldParams verify = new FieldParams();
		
		// Ensure data does not already exist in the database 
		verify.put(User.USERNAME, transact.get(User.USERNAME));
		Require.requireUnique(BusinessClass.USER, verify, request);
		
		// Update User id
		transact.put(User.ID, user != null ? Long.toString(((User)user).getId() + 1) : "0");
		
		// If non-admin then set account to pending 
		if (request.getCheckpoint() != Checkpoint.ADMIN) 
			transact.put(User.CHECKPOINT, Checkpoint.PENDING);
		
		// Insert user 
		return new Resultset(Server.database.insert(BusinessClass.USER, request.getTransaction()));
	}
	
	/**
	 * Deletes user and all data associated with them
	 * @param request 
	 * @return total number of records removed 
	 */
	public Resultset deleteUser(Request request) {
		FieldParams userdata = new FieldParams();
		String userid;
		int total;
		
		// If admin then allowed to remove other accounts 
		if (request.getCheckpoint() == Checkpoint.ADMIN) {
			FieldParams query = request.getQuery();
			userid = query.get(User.ID);
		} else {
			userid = Long.toString(request.getUserId());
		}
		
		// set parameters needed to remove user data
		userdata.put(Info.USERID, userid);
		userdata.put(User.ID, userid);
		
		// Remove all records associated with this id
		total = Server.database.delete(BusinessClass.USERINFO, userdata);
		total += Server.database.delete(BusinessClass.ACCOUNT, userdata);
		total += Server.database.delete(BusinessClass.USER, userdata);
		
		return new Resultset(total);
	}
	
	/**
	 * Performs query of User records 
	 * @param request conditions users must have to be in resultset
	 * @return 0 to n records
	 */
	public Resultset getUser(Request request) throws RequestException {
		if (request.getCheckpoint() != Checkpoint.ADMIN)
			Require.requireSelfQuery(request);
		
		return Server.database.select(BusinessClass.USER, request.getQuery());
	}
	
	/**
	 * Allows everything except user id to be updated 
	 * @param request
	 * @return
	 */
	public Resultset setUser(Request request) throws RequestException {
		FieldParams query = request.getQuery();
		FieldParams trans = request.getTransaction();
		
		// If user account Ensure they are updating their account only 
		if (request.getCheckpoint() != Checkpoint.ADMIN) {
			Require.requireSelfQuery(request);
			
			// Users are not allowed to update checkpoints 
			trans.remove(User.CHECKPOINT);
		}
		
		// Ensure we do not change user id
		trans.remove(User.ID);
		
		// Perform update
		return new Resultset(Server.database.update(BusinessClass.USER, query, trans));
	}
	
	///
	//	USERINFO
	///
	
	/**
	 * Generates UserInfo record
	 * @param request
	 * @return modified record count should equal 1
	 * @throws RequestException
	 */
	public Resultset createUserInfo(Request request) throws RequestException {
		FieldParams conditions = new FieldParams();
		
		// UserInfo record must not exist 
		Require.requireUnique(BusinessClass.USERINFO, request.getTransaction(), request);
		
		// Set condition params
		conditions.put(User.ID, Long.toString(request.getUserId()));
		
		// User account must exist before it can be created 
		Require.requireExists(BusinessClass.USER, conditions, request);
		
		return new Resultset(Server.database.insert(BusinessClass.USERINFO, request.getTransaction()));
	}
	
	/**
	 * Acquires UserInfo records
	 * @param request
	 * @return modified record count should equal 1 for non-admin and 0 to many for Admin
	 * @throws RequestException
	 */
	public Resultset getUserInfo(Request request) throws RequestException { 
		// For NON-ADMINS only personal account information should be accessible 
		if (request.getCheckpoint() != Checkpoint.ADMIN) 
			Require.requireSelfQuery(request);
		
		return Server.database.select(BusinessClass.USERINFO, request.getQuery());
	}
	
	public Resultset setUserInfo(Request request) throws RequestException {
		FieldParams transact = request.getTransaction();
		
		// For NON-ADMINS only personal account information should be accessible 
		if (request.getCheckpoint() != Checkpoint.ADMIN) 
			Require.requireSelfQuery(request);
		
		// We are not allowed to change the userid
		transact.remove(Info.USERID);
		
		return new Resultset(Server.database.update(BusinessClass.USERINFO, request.getQuery(), transact));
	}
	
	///
	//	ACCOUNT
	///
	
	public Resultset createAccount(Request request, String type) {
		FieldParams transact = request.getTransaction();
		BusinessObject largest;
		String number;
		
		// Get account number 
		largest = GenericHelper.getLargest(BusinessClass.ACCOUNT, Arrays.asList(new String[] { Account.NUMBER }));
		number = largest == null ? "0" : Long.toString(((Account)largest).getNumber() + 1);
		
		// Set account details
		transact.put(Info.USERID, Long.toString(request.getUserId())));
		transact.put(Account.TYPE, type);
		transact.put(Account.STATUS, AccountStatus.PENDING);
		transact.put(Account.NUMBER, number);
		
		return new Resultset(Server.database.insert(BusinessClass.ACCOUNT, transact));
	}
	
	public Resultset deleteAccount(Request request) throws RequestException {
		return null;
	}
	
	
	public Resultset getAccount(Request request) throws RequestException {
		// For NON-ADMINS only personal account information should be accessible 
		if (request.getCheckpoint() != Checkpoint.ADMIN) 
			Require.requireSelfQuery(request);
				
		return Server.database.select(BusinessClass.ACCOUNT, request.getQuery());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
