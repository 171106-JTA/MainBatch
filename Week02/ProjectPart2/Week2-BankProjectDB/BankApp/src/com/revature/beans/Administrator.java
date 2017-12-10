package com.revature.beans;


/**
 * @author Matthew
 * Class models an Adminsitrator Account
 * Administrators have access to an Administrator Dashboard that allows
 * the Admin to block and unblock Customers, as well as promoting a 
 * Customer Account to an Admin account.
 */
public class Administrator extends User{
	
	private static final long serialVersionUID = 1L;
	final boolean isAdmin = true;
	
	public Administrator(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Overriden toString
	 */
	@Override
	public String toString() {
		return "Administrator[username= " + username + ", password= "
				+ password + "]";
	}
	
	/**
	 * Overwritten equals
	 */
	public boolean equals(Administrator admin) {
		boolean result = false;
		if (this.username.equals(admin.username) && this.password.equals(admin.password)){
			result = true;
		}
		return result;
	}
	
}
