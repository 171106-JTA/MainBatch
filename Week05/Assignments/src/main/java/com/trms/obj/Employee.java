package com.trms.obj;

import java.util.HashMap;

public class Employee {
	private String firstName;
	private String lastName;
	private String department;
	private String position; 
	private String supervisor;
	private String loginUserId;
	private String loginPassword;
	private ContactInformation contactInfo;
	private ContactInformation emergencyContact; 
	
	
	public Employee(String firstName, String lastName, String position, String department, String loginUserId, String loginPassword) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position; 
		this.department = department;
		this.loginUserId = loginUserId;
		this.loginPassword = loginPassword; 
		
		contactInfo = new ContactInformation();
		contactInfo.setFirstName(firstName);
		contactInfo.setLastName(lastName);
	}
	
	public Employee(String firstName, String lastName, String loginUserId, String loginPassword, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginUserId = loginUserId;
		this.loginPassword = loginPassword; 
		this.contactInfo = new ContactInformation(); 
		
		this.contactInfo.setEmail(email);
		this.contactInfo.setFirstName(firstName);
		this.contactInfo.setLastName(lastName);
		
		this.emergencyContact = new ContactInformation(); 		
	}
	public Employee(String firstName, String lastName, String loginUserId, String loginPassword, 
			String department, String position, String supervisor, ContactInformation contactInformation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginUserId = loginUserId;
		this.loginPassword = loginPassword; 
		this.department = department; 
		this.position = position;
		this.supervisor = supervisor;
		this.contactInfo = contactInformation; 
		
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
	public String getFullName() {
		return (firstName + " " + lastName); 
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position; 
	}
	public void setPosition(String position) {
		this.position = position; 
	}
	public String getSupervisor() {
		return supervisor; 
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor; 
	}
	
	public ContactInformation getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInformation contactInfo) {
		this.contactInfo = contactInfo;
	}


	public ContactInformation getEmergencyContact() {
		return emergencyContact;
	}


	public void setEmergencyContact(ContactInformation emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", department=" + department
				+ ", position=" + position + ", supervisor=" + supervisor + ", loginUserId=" + loginUserId
				+ ", loginPassword=" + loginPassword + ", contactInfo=" + contactInfo;
	}



	
	
	
}
