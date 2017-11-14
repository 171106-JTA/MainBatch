package com.revature.businessobject.user;

/**
 * Defines user privileges 
 * @author Antony Lulciuc
 */
public class Checkpoint {
	// User has no privileges 
	public static final String NONE = "none";
	
	// Does not have access to system
	public static final String BLOCKED = "blocked";
	
	// User account requesting to be created 
	public static final String PENDING = "pending";
	
	// ADMIN has access to all accounts
	public static final String ADMIN = "admin";
	
	// CUSTOMER has access only to their account
	public static final String CUSTOMER = "customer";
}
