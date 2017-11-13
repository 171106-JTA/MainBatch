package com.revature.businessobject.user;

import java.util.HashMap;

import com.revature.businessobject.BusinessObject;

/**
 * Used to identify user and there role
 * @author Antony Lulciuc
 */
public class User extends BusinessObject implements Comparable<User> {
	/**
	 * Fields used to convert/access data from FieldParams
	 * @see FieldParams
	 */
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CHECKPOINT = "checkpoint";
	
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
	private String checkpoint;
	
	/**
	 * Initializes user with specified id
	 * @param id used throughout the system to access account data 
	 * @param username account name
	 * @param password account password
	 * @param role user account privileges 
	 */
	public User(long id, String username, String password, String checkpoint) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.checkpoint = checkpoint;
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
	public String getCheckpoint() {
		return checkpoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((checkpoint == null) ? 0 : checkpoint.hashCode());
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
		if (checkpoint != other.checkpoint)
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
		return "User [id=" + id + ", username="  + username + ", password=" + password + ", checkpoint=" + checkpoint + "]";
	}

	@Override
	public int compareTo(User user) {
		int result;
		
		if ((result = Long.compare(id, user.getId())) == 0)
			if ((result = username.compareTo(user.getUsername())) == 0)
				if ((result = password.compareTo(user.getPassword())) == 0)
					return checkpoint.compareTo(user.getCheckpoint());
		
		return result;
	}
}
