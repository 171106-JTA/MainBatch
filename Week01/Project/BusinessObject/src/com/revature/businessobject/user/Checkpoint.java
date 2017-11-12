package com.revature.businessobject.user;

/**
 * Defines user privileges 
 * @author Antony Lulciuc
 */
public enum Checkpoint {
	// User has no privileges 
	NONE,
	
	// Does not have access to system
	BLOCKED,
	
	// User account requesting to be created 
	PENDING, 
	
	// ADMIN has access to all accounts
	ADMIN,
	
	// CUSTOMER has access only to their account
	CUSTOMER
}
