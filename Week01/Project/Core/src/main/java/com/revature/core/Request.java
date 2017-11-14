package com.revature.core;

import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;

/**
 * Used to interact between Server and application 
 * @author Antony Lulciuc
 */
public class Request {
	private int sessionId;
	private long userId;
	private String checkpoint;
	private String route;
	private String transtype;
	private FieldParams query;
	private FieldParams transaction;
	
	/**
	 * Sets necessary data to process a request
	 * @param loginResponse - received on successful login
	 * @param route - which processor to use
	 * @param transtype - which action in processor to preform
	 * @param query - used as lookup for desired record
	 * @param transaction - used for inserts and update statements 
	 */
	public Request(FieldParams loginResponse, String route, String transtype, FieldParams query, FieldParams transaction) {
		super();
		this.sessionId = loginResponse.get(User.SESSIONID) == null ? 0 : Integer.parseInt(loginResponse.get(User.SESSIONID));
		this.userId = loginResponse.get(User.ID) == null ? 0 : Long.parseLong(loginResponse.get(User.ID));
		this.route = route.toUpperCase();
		this.transtype = transtype.toUpperCase();
		this.query = query;
		this.transaction = transaction;
		this.checkpoint = Checkpoint.NONE;
	}
	
	/**
	 * Sets necessary data to process a request
	 * @param sessionId - id of successfully created session
	 * @param userId - id of account which exists on that session
	 * @param route - which processor to use
	 * @param transtype - which action in processor to preform
	 * @param query - used as lookup for desired record
	 * @param transaction - used for inserts and update statements 
	 */
	public Request(int sessionId, long userId, String route, String transtype,
			FieldParams query, FieldParams transaction) {
		super();
		this.sessionId = sessionId;
		this.userId = userId;
		this.route = route.toUpperCase();
		this.transtype = transtype.toUpperCase();
		this.query = query;
		this.transaction = transaction;
		this.checkpoint = Checkpoint.NONE;
	}
	
	/**
	 * @return id of session to send the request to
	 */
	public int getSessionId() {
		return sessionId;
	}

	/**
	 * @return id of user who activated session
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @return permissions user has
	 */
	public String getCheckpoint() {
		return checkpoint;
	}

	/**
	 * Used to modify user checkpoint (used by session [ignores users use])
	 * @param checkpoint new checkpoint value
	 */
	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}

	/**
	 * @return which processor to use
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * Assigns processor to use
	 * @param route new route
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return determines what action to perform on processor
	 */
	public String getTranstype() {
		return transtype;
	}

	/**
	 * Assigns action to perform on processor
	 * @param transtype
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * @return used for locating desired records 
	 */
	public FieldParams getQuery() {
		return query;
	}

	/**
	 * Updates query params
	 * @param query params
	 * @see FieldParams
	 */
	public void setQuery(FieldParams query) {
		this.query = query;
	}

	/**
	 * @return data/values used to create/update record
	 */
	public FieldParams getTransaction() {
		return transaction;
	}

	/**
	 * Updates data params
	 * @param transaction params
	 * @see FieldParams
	 */
	public void setTransaction(FieldParams transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "Request [sessionId=" + sessionId + ", userId=" + userId
				+ ", checkpoint=" + checkpoint + ", route=" + route
				+ ", transtype=" + transtype + ", query=" + query
				+ ", transaction=" + transaction + "]";
	}
}
