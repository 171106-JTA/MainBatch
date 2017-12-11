package com.project1.classes;

public class Employee {
	String employeeID;
	String password;
	String fname;
	String mname;
	String lname;
	String email;
	int empPos;
	public Employee(String employeeID, String password, String fname, String mname, String lname, String email) {
		super();
		this.employeeID = employeeID;
		this.password = password;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.email = email;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
		this.employeeID = "";
		this.password = "";
		this.fname = "";
		this.mname = "";
		this.lname = "";
		this.email = "";
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getEmpPos() {
		return empPos;
	}
	public void setEmpPos(int empPos) {
		this.empPos = empPos;
	}
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", password=" + password + ", fname=" + fname + ", mname=" + mname
				+ ", lname=" + lname + ", email=" + email + ", empPos=" + empPos + "]";
	}
	
	
	
	

}
