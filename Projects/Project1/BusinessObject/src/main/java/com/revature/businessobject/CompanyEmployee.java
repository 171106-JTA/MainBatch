package com.revature.businessobject;

public class CompanyEmployee implements BusinessObject {
	private Integer id;
	private Integer stateCityId;
	private Integer departmentId;
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String address;
	private String phoneNumber;
	private String email;
	
	public CompanyEmployee() {
		// do nothing
	}
	
	public CompanyEmployee(Integer id, Integer stateCityId,
			Integer departmentId, Integer employeeId, String firstName,
			String lastName, String address, String phoneNumber, String email) {
		super();
		this.id = id;
		this.stateCityId = stateCityId;
		this.departmentId = departmentId;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateCityId() {
		return stateCityId;
	}

	public void setStateCityId(Integer stateCityId) {
		this.stateCityId = stateCityId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phonenumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
