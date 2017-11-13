package com.revature.businessobject.user;

/**
 * Defines User with administrative privileges 
 * @author Antony Lulciuc
 */
public class Admin extends User {
	/**
	 * Creates user with administrative privileges 
	 * @param id user primary key
	 * @param username account name
	 * @param password account password
	 */
	public Admin(long id, String username, String password) {
		super(id, username, password, Checkpoint.ADMIN);
	}
}
