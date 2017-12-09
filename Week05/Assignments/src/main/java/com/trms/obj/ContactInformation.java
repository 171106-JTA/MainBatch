package com.trms.obj;

public class ContactInformation {
	private String firstName;
	private String lastName; 
	private String email;
	private String phone;
	private String streetAddr;
	private String city;
	private String state;
	private String zipCode; 
	
	
	
	
	public ContactInformation(String firstName, String lastName, String email, String phone, 
			String streetAddr, String city, String state, String zipCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.streetAddr = streetAddr;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode; 
	}
	public ContactInformation() {
		firstName = "N/A"; 
		lastName = "N/A";
		phone = "N/A"; 
		email = "N/A"; 
		streetAddr = "N/A";
		city = "N/A"; 
		state = "N/A"; 
		zipCode = "N/A";
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreetAddr() {
		return streetAddr;
	}
	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode; 
	}
	@Override
	public String toString() {
		return "ContactInformation [Name=" + firstName + " " + lastName + ", phone: " + phone
				+ ", email: " + email + "; Address: " + streetAddr + ", " + city
				+ ", " + state + ", zip code: " + zipCode + "]";
	}
	
	
	
}
