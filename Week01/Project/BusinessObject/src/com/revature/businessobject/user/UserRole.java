package com.revature.businessobject.user;

/**
 * Defines user privileges 
 * @author Antony Lulciuc
 */
public enum UserRole {
	// User has no privileges 
	NONE,
	
	// ADMIN has access to all accounts
	ADMIN,
	
	// CUSTOMER has access only to their account
	CUSTOMER
}
