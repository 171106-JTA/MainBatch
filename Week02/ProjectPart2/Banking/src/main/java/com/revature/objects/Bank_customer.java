/**
 * Class storing the customers
 * 
 * * Base on the requirements, i didn't see necessary to create
 * a user class bescause the system as described is like 
 * an environment with members managing.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */package com.revature.objects;

import java.util.Date;

import com.revature.generalmethods.GeneralFunctions;

public class Bank_customer {
	/*
	 * This class handles the actual bank customers.
	 * In the current system, all users are customers.
	 * */
	
	/**
	 * 
	 */
	
	//Properties
	@SuppressWarnings("unused")
	private int customerID;
	private Date since;				//Customer since
	
	private String userName;		//User credentials
	private String pass;
	private int roleID = 1;			//Default value set to 1 for simple user
	
	private int personID;
	private int isActive = 0;
	
		
	//Constructor
	/*public Bank_customer(String fname, String lname, String adress, String city, String state, String areacode, String country, String email, String phone, String ssn, Date dob,
			Date since, String userName, String password, int roleID, boolean isActive) {
		super(fname, lname, adress, city, state, areacode, country, email, phone, ssn, dob);
		this.since = since;
		this.userName = userName;
		this.pass = password;
		this.roleID = roleID;
		this.personID = super.getId();
		this.isActive = 0;
		
	}*/
	//public Bank_customer(java.sql.Date since, String username, String password, int roleID, int personid, int isactive) {
	public Bank_customer(Date since, String username, String password, int roleID, int personid, int isactive) {
		this.since = since;
		this.userName = username;
		this.pass = password;
		this.roleID = roleID;
		this.personID = personid;
		this.isActive = isactive;
	}
	
	public Bank_customer(int id, Date since, String username, String password, int roleID, int personid, int isactive) {
		this.customerID = id;
		this.since = since;
		this.userName = username;
		this.pass = password;
		this.roleID = roleID;
		this.personID = personid;
		this.isActive = isactive;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return this.pass;
	}
	public void setPassword(String password) {
		this.pass = password;
	}

	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int isActive() {
		return isActive;
	}
	public void activate() {
		this.isActive = 1;
	}
	public void desactivate() {
		this.isActive = 0;
	}
	
	public void promote() {
		this.setRoleID(0);
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}
		
	@Override
	public String toString() {
		return super.toString() +
				"\nCustomer\n-------------\nSince:\t\t" + new GeneralFunctions<Integer>().formatDate(since, "MM/dd/yyyy") + 
				"\nUsername:\t" + userName + "\nRoleID:\t" + roleID + "\nisActive:\t" + isActive +  "\nPassword:\t" + pass + 
				"\n-------------";
	}



}
