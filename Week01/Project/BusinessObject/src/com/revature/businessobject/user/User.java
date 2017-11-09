package com.revature.businessobject.user;

import java.util.HashMap;

import com.revature.businessobject.BusinessObject;

/**
 * Used to identify user and there role
 * @author Antony Lulciuc
 */
public class User extends BusinessObject implements Comparable<User> {
	/**
	 * Unique identifier used to pull user data 
	 */
	private long id;
	
	/**
	 * Name associated with user
	 */
	private String username;
	
	/**
	 * Password used for user to access account information
	 */
	private String password;
	
	/**
	 * Value represents account privileges 
	 */
	private UserRole role;
	
	/**
	 * Initializes user with specified id
	 * @param id used throughout the system to access account data 
	 * @param username account name
	 * @param password account password
	 * @param role user account privileges 
	 */
	public User(long id, String username, String password, UserRole role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	/**
	 * @return user id (PRIMARY KEY)
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Account name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return Account password
	 */
	public String getPassword() {
		return password;
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
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role != other.role)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username="  + username + ", password=" + password + ", role=" + role + "]";
	}

	@Override
	public int compareTo(User user) {
		int result;
		
		if ((result = Long.compare(id, user.getId())) == 0)
			if ((result = username.compareTo(user.getUsername())) == 0)
				if ((result = password.compareTo(user.getPassword())) == 0)
					return Integer.compare(role.ordinal(), user.getRole().ordinal());
		
		return result;
	}
}
