package com.revature.processor.handler;

import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.server.Server;

public final class UserRequestHandler {
	/**
	 * Performs select query on database for user with specified username and password
	 * @param request request with username and password
	 * @return User BusinessObject if found else empty resultset 
	 */
	public Resultset login(Request request) {
		return Server.database.select("User", request.getQuery());
	}
	
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
	public Resultset setUser(Request request) {
		FieldParams query = request.getQuery();
		FieldParams trans = request.getTransaction();
		
		// Ensure we do not change user id
		trans.remove("id");
		
		// Perform update
		Server.database.update(BusinessClass.USER, query, trans);
		
		return new Resultset(Server.database.update(BusinessClass.USER, query, trans));
	}
	
}
