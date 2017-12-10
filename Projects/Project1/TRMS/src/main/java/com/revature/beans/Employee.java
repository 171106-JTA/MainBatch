package com.revature.beans;

import java.sql.Date;

public class Employee {
	private Integer id;
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String zip;
	private int super_id;
	private int dept_id;
	private String department;
	private String email;
	private String password;
	private int title_id;
	private String title;
	
	private String superFirstname;
	private String superLastname;
	
	private double funds;

	public int getTitle_id() {
		return title_id;
	}

	public void setTitle_id(int title_id) {
		this.title_id = title_id;
	}

	public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String fname, String lname, String address,
					int super_id, int dept_id, String email,
					String password, String title) {
		this.id = null;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.super_id = super_id;
		this.dept_id = dept_id;
		this.email = email;
		this.password = password;
		this.title = title;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSuper_id() {
		return super_id;
	}

	public void setSuper_id(int super_id) {
		this.super_id = super_id;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSuperFirstname() {
		return superFirstname;
	}

	public void setSuperFirstname(String superFirstname) {
		this.superFirstname = superFirstname;
	}

	public String getSuperLastname() {
		return superLastname;
	}

	public void setSuperLastname(String superLastname) {
		this.superLastname = superLastname;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fname=" + fname + ", lname=" + lname + ", address=" + address + ", super_id="
				+ super_id + ", dept_id=" + dept_id + ", email=" + email + ", password=" + password + ", title=" + title
				+ "]";
	}
	
}
