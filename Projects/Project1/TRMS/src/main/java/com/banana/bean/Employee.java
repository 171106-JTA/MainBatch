package com.banana.bean;

public class Employee {
	private String username;
	private String password;
	private int empId;
	private double currAmount;
	
	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Employee(String username, String password, int empId) {
		this.username = username;
		this.password = password;
		this.empId = empId;
	}
	
	public Employee(String username, String password, int empId, double currAmount) {
		this.username = username;
		this.password = password;
		this.empId = empId;
		this.currAmount = currAmount;
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

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "Employee [username=" + username + ", password=" + password + ", empId=" + empId + "]";
	}
	
}
