package com.revature.beans;

public class EmployeeAccount {
	private String username;
	private String password;
	private String firstname;
	private String middlename;
	private String lastname;
	private String email;
	private String address;
	private String city;
	private String state;
	private boolean islocked;
	private boolean isadmin;
	private int employeeid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public boolean isLocked() {
		return islocked;
	}

	public void setLocked(boolean islocked) {
		this.islocked = islocked;
	}

	public boolean isAdmin() {
		return isadmin;
	}

	public void setAdmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	
	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	
	public String toJSON() {
		return "{"
				+ "\"username\": " + "\"" + this.getUsername() + "\","
				+ "\"firstname\": " + "\"" + this.getFirstname() + "\","
				+ "\"middlename\": " + "\"" + this.getMiddlename() + "\","
				+ "\"lastname\": " + "\"" + this.getLastname() + "\","
				+ "\"email\": " + "\"" + this.getEmail() + "\","
				+ "\"address\": " + "\"" + this.getAddress() + "\","
				+ "\"city\": " + "\"" + this.getCity() + "\","
				+ "\"state\": " + "\"" + this.getState() + "\","
				+ "\"islocked\": " + this.isLocked() + ","
				+ "\"isadmin\": " + this.isAdmin() + ","
				+ "\"employeeid\": " + this.getEmployeeid()
				+ "}";
	}
}
