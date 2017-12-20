package com.revature.beans;

public class Employee {
	private String fname;
	private String lname;
	private int empId;
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getlname() {
		return lname;
	}
	public void setlname(String lname) {
		this.lname = lname;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Employee(String fname, String lname, int empId) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "Employee [fname=" + fname + ", lname=" + lname + ", empId=" + empId + "]";
	}
	public Employee() {
		super();
	}
	
	
	
}
