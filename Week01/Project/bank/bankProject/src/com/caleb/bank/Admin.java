package com.caleb.bank;

/**
 * The Admin class represents an admin at a bank. The admin has a name, email, and password. 
 * The admin has authority over the users at the bank
 * @author calebschumake
 *
 */
class Admin implements java.io.Serializable {
	String name, email, password;
	
	/**
	 * initilizes the admin object with a name, email, and password
	 * @param name
	 * @param email
	 * @param password
	 */
	Admin(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	
	 /**
	  * returns the name of the admiin
	  * @return String
	  */
	public String getName() {
		return name;
	}
	
	/**
	 * returns the email of the admin
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * returns the password of the admin
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

}