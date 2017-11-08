package com.revature.businessobject.user;

import com.revature.businessobject.BusinessObject;

/**
 * Used to identify user and there role
 * @author Antony Lulciuc
 */
public abstract class User extends BusinessObject {
	/**
	 * Unique identifier used to pull user data 
	 */
	private long id;
	
	/**
	 * Initializes user with specified id
	 * @param id used throughout the system to access account data 
	 */
	User(long id) {
		super();
		this.id = id;
	}
	
	/**
	 * @return user id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Value represents user privileges (what they do and do not have access to).
	 * @return user account status
	 */
	abstract public UserRole getRole();
}
