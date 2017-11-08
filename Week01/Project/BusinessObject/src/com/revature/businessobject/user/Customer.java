package com.revature.businessobject.user;

/**
 * Defines User with customer privileges 
 * @author Antony Lulciuc
 */
public class Customer extends User {
	/**
	 * Creates user with customer privileges 
	 * @param id user primary key
	 */
	public Customer(long id) {
		super(id, UserRole.NONE);
	}
}
