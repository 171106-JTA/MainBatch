package com.revature.trms.model;

import java.sql.Time;

import org.apache.log4j.Logger;

public class Employee {
	private int userId;
	private String username;
	private String password;
	private String email;
	private String fname;
	private String lname;
	private Time creationTime;
	private int userType;
	private int supervisorId;

	public Employee(int userId, String username, String password, String email, String fname, String lname,
			Time creationTime, int userType, int supervisorId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.creationTime = creationTime;
		this.userType = userType;
		this.supervisorId = supervisorId;
	}

	public static int usertypeString2Int(String usertype) {
		if(usertype.equals("Admin")) {
			return 1;
		}
		else if(usertype.equals("Department_Head")) {
			return 2;
		}
		else if(usertype.equals("Supervisor")) {
			return 3;
		}
		else {
			return 4;
		}
	}
	
	public Employee(int userId, String username, String password, String email, String fname, String lname,
			Time creationTime, int userType) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.creationTime = creationTime;
		this.userType = userType;
		this.supervisorId = 21; //admin employee ID
	}

	public Employee(String username, String password, String email, String fname, String lname) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.supervisorId = 21; //admin employee ID
	}

	
	@Override
	public String toString() {
		return "Employee [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", fname=" + fname + ", lname=" + lname + ", creationTime=" + creationTime + ", userType=" + userType
				+ ", supervisorId=" + supervisorId + "]";
	}

	public Employee() {
		this.supervisorId = 21; //admin employee ID
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Time getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Time creationTime) {
		this.creationTime = creationTime;
	}

	public String getUserType() {
		switch (this.userType) {
		case 1:
			return "Admin";
		case 2:
			return "Department_Head";
		case 3:
			return "Supervisor";
		default:
			return "Default_Employee";
		}
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}

}
