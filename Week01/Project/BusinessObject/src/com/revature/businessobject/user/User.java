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
	public User(long id, UserRole role) {
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

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + "]";
	}
}
