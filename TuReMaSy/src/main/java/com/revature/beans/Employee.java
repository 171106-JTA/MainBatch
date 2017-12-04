package com.revature.beans;

public class Employee {
	private int empID;
	private int deptNum;
	private int status;
	private String username;
	private String password;
	private String email;
	private String fname;
	private String lname;
	
//	public Employee(){
//		super();
//	}
	public Employee(int empID, int deptNum, int status, String username, String password, 
			String email, String fname, String lname){
		super();
		this.empID = empID;
		this.deptNum = deptNum;
		this.status = status;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
	}
	public Employee(){
		super();
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public int getDeptNum() {
		return deptNum;
	}
	public void setDeptNum(int deptNum) {
		this.deptNum = deptNum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	@Override
	public String toString() {
		return "Employee [empID=" + empID + ", deptNum=" + deptNum + ", status=" + status + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", fname=" + fname + ", lname=" + lname + "]";
	}
	
}
