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
	
	public UserInfo(Integer userId, CompanyEmployee employee) {
		super();
		this.userId = userId;
		this.stateCityId = employee.getStateCityId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.address = employee.getAddress();
		this.email = employee.getEmail();
		this.phoneNumber = employee.getPhoneNumber();
	}
	
	public UserInfo(Integer id, Integer userId, Integer stateCityId,
			String firstName, String lastName, String phoneNumber,
			String address, String email) {
		super();
		this.id = id;
		this.userId = userId;
		this.stateCityId = stateCityId;
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setAddress(address);
		setEmail(email);
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
		this.firstName = BusinessObject.validateString(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = BusinessObject.validateString(lastName);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = BusinessObject.validateString(phoneNumber);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = BusinessObject.validateString(address);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = BusinessObject.validateString(email);
	}
}
