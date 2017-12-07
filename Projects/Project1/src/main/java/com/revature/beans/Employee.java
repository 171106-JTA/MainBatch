package com.revature.beans;

public class Employee {
	private String username, fname, lname, phoneNumber, address, city, state, zipcode;
	private Title title;
	private int password;
	
	public Employee(String username, String fname, String lname) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
	}
	
	public Employee(String username, String fname, String lname, String title) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.title = Title.getTitle(title);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fname
	 * @param lname
	 * @param address
	 * @param zipcode
	 */
	public Employee(String username, int password, String fname, String lname, String phoneNumber, Title title,
			String address,	String zipcode, String city, String state) {
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.phoneNumber = phoneNumber;
		this.title = title;
		this.city = city;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.state = state;
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}