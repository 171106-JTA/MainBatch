package com.revature.processor.handler;

import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;
import com.revature.server.session.require.Require;

public final class UserRequestHandler {
	/**
	 * Performs select query on database for user with specified username and password
	 * @param request request with username and password
	 * @return User BusinessObject if found else empty resultset 
	 */
	public Resultset login(Request request) {
		return Server.database.select("User", request.getQuery());
	}
	
	/**
	 * Creates new user accounts 
	 * @param request
	 * @return
	 */
	public Resultset createUser(Request request) {
		return new Resultset(Server.database.insert(BusinessClass.USER, request.getTransaction()));
	}
	
	/**
	 * Performs query of User records 
	 * @param request conditions users must have to be in resultset
	 * @return 0 to n records
	 */
	public Resultset getUser(Request request) {
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
		
		// If user  account Ensure they are updating their account only 
		if (request.getCheckpoint() == Checkpoint.CUSTOMER) {
			Require.requireSelf(request);
			
			// Users are not allowed to update checkpoints 
			trans.remove(User.CHECKPOINT);
		}
		
		// Ensure we do not change user id
		trans.remove(User.ID);
		
		// Perform update
		Server.database.update(BusinessClass.USER, query, trans);
		
		return new Resultset(Server.database.update(BusinessClass.USER, query, trans));
	}
	
	public Resultset getUserInfo(Request request) throws RequestException { 
		// For NON-ADMINS only personal account information should be accessible 
		if (request.getCheckpoint() == Checkpoint.CUSTOMER) 
			Require.requireSelf(request);
		
		return Server.database.select(BusinessClass.USERINFO, request.getQuery());
	}
	
	
	public Resultset getAccount(Request request) throws RequestException {
		// For NON-ADMINS only personal account information should be accessible 
		if (request.getCheckpoint() == Checkpoint.CUSTOMER) 
			Require.requireSelf(request);
				
		return Server.database.select(BusinessClass.ACCOUNT, request.getQuery());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
