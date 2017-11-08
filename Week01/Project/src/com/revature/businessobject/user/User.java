package com.revature.businessobject.user;

import com.revature.businessobject.BusinessObject;

/**
 * Used to identify user and there role
 * @author Antony Lulciuc
 */
public class User extends BusinessObject {
	/**
	 * Unique identifier used to pull user data 
	 */
	private long id;
	
	/**
	 * Value represents account privileges 
	 */
	private UserRole role;
	
	/**
	 * Initializes user with specified id
	 * @param id used throughout the system to access account data 
	 * @param role user account privileges 
	 */
	User(long id, UserRole role) {
		super();
		this.id = id;
		this.role = role;
	}
	
	/**
	 * @return user id (PRIMARY KEY)
	 */
	public long getId() {
		return id;
	}

	/**
	 * Value represents user privileges (what they do and do not have access to).
	 * @return user account status
	 */
	public UserRole getRole() {
		return role;
	}
}
