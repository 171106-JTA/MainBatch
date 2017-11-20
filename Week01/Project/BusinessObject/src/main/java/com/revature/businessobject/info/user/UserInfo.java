package com.revature.businessobject.info.user;

import com.revature.businessobject.info.Info;

/**
 * User contact information
 * @author Antony Lulciuc
 *
 */
public class UserInfo extends Info {
	public static final String SSN = "ssn";
	public static final String EMAIL = "email";
	public static final String PHONENUMBER = "phone_number";
	public static final String FIRSTNAME = "first_name";
	public static final String LASTNAME = "last_name";
	public static final String STATECITYID = "state_city_id";
	public static final String ADDRESS1 = "address_1";
	public static final String ADDRESS2 = "address_2";
	public static final String POSTALCODE = "postal_code";
	public static final String STATUSID = "status_id";
	public static final String STATE = "state";
	public static final String CITY = "city";
	private long ssn;
	private String email;
	private String phonenumber;
	private String address1;
	private String address2;
	private String firstname;
	private String lastname;
	private String postalcode;
	private long stateCityId;
	private long statusId;

	public UserInfo(long userId) {
		super(userId);
	}
	
	public UserInfo(long userId, long ssn, String email, String phonenumber,
			String address1, String address2, String firstname, String lastname,
			String postalcode, long stateCityId, long statusId) {
		super(userId);
		this.ssn = ssn;
		this.email = email;
		this.phonenumber = phonenumber;
		this.address1 = address1;
		this.address2 = address2;
		this.firstname = firstname;
		this.lastname = lastname;
		this.postalcode = postalcode;
		this.stateCityId = stateCityId;
		this.statusId = statusId;
	}

	public long getSsn() {
		return ssn;
	}

	public void setSsn(long ssn) {
		this.ssn = ssn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public long getStateCityId() {
		return stateCityId;
	}

	public void setStateCityId(long stateCityId) {
		this.stateCityId = stateCityId;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}	
}
