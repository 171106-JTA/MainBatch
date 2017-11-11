package com.revature.businessobject.user;

/**
 * Defines User with customer privileges 
 * @author Antony Lulciuc
 */
public class Customer extends User {
	/**
	 * Creates user with customer privileges 
	 * @param id user primary key
	 * @param username account name
	 * @param password account password
	 */
	public Customer(long id, String username, String password) {
		super(id, username, password, Checkpoint.CUSTOMER);
	}
}
