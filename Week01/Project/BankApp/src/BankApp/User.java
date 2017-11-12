package BankApp;

import java.io.Serializable;

/**
 * @author Matthew
 * This class models users who will use the BankApp
 */
public abstract class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String username;
	String password;
	boolean isAdmin;
	
	/**
	 * getter for isAdmin
	 * @return
	 * boolean indicating whether the User is an Admin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * setter for isAdmin
	 * @return
	 * boolean that isAdmin will be set to
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	/**
	 * getter for username
	 * @return
	 * String holding the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * setter for username
	 * @param
	 * String that username will be set to
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * getter for password
	 * @return
	 * String holding the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * setter for password
	 * @param
	 * String that password will be set to
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}