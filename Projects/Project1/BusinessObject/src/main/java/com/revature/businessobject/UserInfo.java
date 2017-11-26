package com.revature.businessobject;

public class UserInfo implements BusinessObject {
	private Integer id;
	private Integer userId;
	private Integer stateCityId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String email;
	
	public UserInfo() {
		// do nothing
	}
	
	public UserInfo(Integer id, Integer userId, Integer stateCityId,
			String firstName, String lastName, String phoneNumber,
			String address, String email) {
		super();
		this.id = id;
		this.userId = userId;
		this.stateCityId = stateCityId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStateCityId() {
		return stateCityId;
	}

	public void setStateCityId(Integer stateCityId) {
		this.stateCityId = stateCityId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
