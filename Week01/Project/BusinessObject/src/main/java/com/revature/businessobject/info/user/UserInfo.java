package com.revature.businessobject.info.user;

import com.revature.businessobject.info.Info;

/**
 * User contact information
 * @author Antony Lulciuc
 *
 */
public class UserInfo extends Info {
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String PHONENUMBER = "phonenumber";
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((phonenumber == null) ? 0 : phonenumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phonenumber == null) {
			if (other.phonenumber != null)
				return false;
		} else if (!phonenumber.equals(other.phonenumber))
			return false;
		return true;
	}
	
	
}
