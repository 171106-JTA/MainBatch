package com.revature.businessobject.user;

public class Admin extends User {
	/**
	 * Creates user with customer privileges 
	 * @param id user primary key
	 */
	public Admin(long id) {
		super(id, UserRole.NONE);
	}
}
