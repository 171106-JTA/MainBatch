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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Info))
			return false;
		Info other = (Info) obj;
		if (userId != other.userId)
			return false;
		return true;
	}
}
