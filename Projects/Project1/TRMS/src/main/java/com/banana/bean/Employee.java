package com.banana.bean;

public class Employee {
	private String username;
	private String password;
	private int empId;
	private double currAmount;
	private double actAmount;
	private int roleId;
	
	public Employee(String username, String password, int empId, double currAmount, int roleId, double actualAmount) {
		this.username = username;
		this.password = password;
		this.empId = empId;
		this.currAmount = currAmount;
		this.roleId = roleId;
		this.actAmount = actualAmount;
	}
	
	
	public double getActAmount() {
		return actAmount;
	}



	public void setActAmount(double actAmount) {
		this.actAmount = actAmount;
	}



	public double getCurrAmount() {
		return currAmount;
	}



	public void setCurrAmount(double currAmount) {
		this.currAmount = currAmount;
	}



	public int getRoleId() {
		return roleId;
	}



	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
