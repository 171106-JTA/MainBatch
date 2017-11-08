package com.revature.businessobject.info;

import com.revature.businessobject.BusinessObject;

/**
 * Used to tie user data to their account 
 * @author Antony Lulciuc
 */
public class Info extends BusinessObject {
	/**
	 * User id (PRIMARY KEY)
	 */
	private transient long userId;

	/**
	 * Initialization of basic user information
	 * @param userId primary key
	 */
	public Info(long userId) {
		super();
		this.userId = userId;
	}

	/**
	 * @return user account id
	 */
	public long getUserId() {
		return userId;
	}
}
