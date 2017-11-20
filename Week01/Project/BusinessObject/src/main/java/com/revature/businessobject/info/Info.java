package com.revature.businessobject.info;

import com.revature.businessobject.BusinessObject;

/**
 * Used to tie user data to their account 
 * @author Antony Lulciuc
 */
public class Info extends BusinessObject{
	public static final String USERID = "user_id";
	
	/**
	 * User id (FORIEGN KEY)
	 */
	private long userId;

	private String status;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Info [userId=" + userId + "]";
	}
}
