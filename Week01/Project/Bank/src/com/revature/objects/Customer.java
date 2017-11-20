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

import java.io.Serializable;
import java.util.Date;

import com.revature.display.MyDisplays;

public class Customer extends Person implements Serializable {
	/*
	 * This class handles the actual bank customers.
	 * In the current system, all users are customers.
	 * */
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 5857116751215858827L;
	private static final long serialVersionUID = 649403316768474280L;

	//Properties
	private Date since;				//Customer since
	
	private String userName;		//User credentials
	private String password;
	private int roleID = 1;			//Default value set to 1 for simple user
	
	private boolean isActive = false;
	
	//Constructor
	public Customer(int customerID, String name, String adress, String email, String phone, String ssn, Date dob,
			Date since, String userName, String password, int roleID, boolean isActive) {
		super(customerID, name, adress, email, phone, ssn, dob);
		this.since = since;
		this.userName = userName;
		this.password = password;
	}
	
		public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public boolean isActive() {
		return isActive;
	}
	public void activate() {
		this.isActive = true;
	}
	public void desactivate() {
		this.isActive = false;
	}
	
	public void promote() {
		this.setRoleID(0);
	}

	
	@Override
	public String toString() {
		return super.toString() +
				"\nCustomer\n-------------\nSince:\t\t" + new MyDisplays<>().formatDate(since, "MM/dd/yyyy") + 
				"\nUsername:\t" + userName + "\nRoleID:\t" + roleID + "\nisActive:\t" + isActive +  "\nPassword:\t" + password + 
				"\n-------------";
	}
	
	
	

	
	
	


}
