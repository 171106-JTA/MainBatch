package com.Project1.bankAccountStuff;

public class User {
	//////////////////////////////////////////////////////////////
	//Member Variables
	//////////////////////////////////////////////////////////////
	
	/**
	 * Username of Client
	 */
	protected String name; 
	
	/**
	 * Password of Client
	 */
	protected String password;
	
	/**
	 * Holds level of permissions for user. 0 = Client, 1 = Admin
	 */
	protected int permissions;
	
	/**
	 * Determines status of cleint's account. 0 = approval pending, 1 = active account, 2 = locked
	 */
	private int status; 
	
	/**
	 * Holds total amount stored in user's account
	 */
	private double accountAmount;
	
	//////////////////////////////////////////////////////////////
	//Constructors
	//////////////////////////////////////////////////////////////
	/**
	 * Initializes a new account, accepting the username and password for the new account
	 * @param name - username for new account
	 * @param password - password for new account
	 */
	User(String name, String password) {
		this.name = name; 
		this.password = password;
		this.permissions = 0; //Initialize new accounts to Client level permissions
		this.status = 0; //Initialize new accounts to require approval
		this.accountAmount = 0; //Initialize new account amounts to 0
	}
	
	
	//////////////////////////////////////////////////////////////
	//Getters and Setters
	//////////////////////////////////////////////////////////////
	/**
	 * Fetch the username
	 * @return Returns the username of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the username
	 * @param name - contains the username
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the password
	 * @return Returns the password of the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set the password
	 * @param password - contains the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Get the user's current permissions level
	 * 0 = Client, 1 = Admin
	 * @return Returns the user's current permissions level
	 */
	public int getPermissions() {
		return permissions;
	}
	
	/**
	 * Set the permission level for the user
	 * @param permissions - Holds the permission level to set
	 */
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	
}
