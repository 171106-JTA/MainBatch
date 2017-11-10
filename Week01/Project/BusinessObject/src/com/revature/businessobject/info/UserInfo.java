package com.revature.businessobject.info;

public class UserInfo extends Info {
	private String email;
	private String address;
	private String phonenumber;
	
	/**
	 * Initializes basic user information
	 * @param userId primary key used to identify user
	 * @param email account email
	 * @param address where they live
	 * @param phonenumber primary phone
	 */
	public UserInfo(long userId, String email, String address, String phonenumber) {
		super(userId);
		this.email = email;
		this.address = address;
		this.phonenumber = phonenumber;
	}

	/**
	 * @return User email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return User primary address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return User primary phone number 
	 */
	public String getPhonenumber() {
		return phonenumber;
	}
}
