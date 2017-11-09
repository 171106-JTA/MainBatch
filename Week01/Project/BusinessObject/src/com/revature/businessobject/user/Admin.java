package com.revature.businessobject.user;

public class Admin extends User {
	/**
	 * Creates user with customer privileges 
	 * @param id user primary key
	 * @param username account name
	 * @param password account password
	 */
	public Admin(long id, String username, String password) {
		super(id, username, password, UserRole.ADMIN);
	}
}
