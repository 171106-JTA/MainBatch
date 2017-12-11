package com.revature.beans;

public class Employee {
	private Integer empid;
	private String fname;
	private String lname;
	private String usrnm;
	private String pswrd;
	private String email;
	private String phone;
	private String dept;
	private String role1;
	private String role2;
	private Integer superid;
	private Double funds;
	
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String fname, String lname, String usrnm, String pswrd, String email, String phone, String dept) {
		this.fname = fname;
		this.lname = lname;
		this.usrnm = usrnm;
		this.pswrd = pswrd;
		this.email = email;
		this.phone = phone;
		this.dept = dept;
	}
	
	public Employee(Integer empid, String fname, String lname, String usrnm, String pswrd, String email, String phone,
			String dept, String role1, String role2, Integer superid, Double funds) {
		this.empid = empid;
		this.fname = fname;
		this.lname = lname;
		this.usrnm = usrnm;
		this.pswrd = pswrd;
		this.email = email;
		this.phone = phone;
		this.dept = dept;
		this.role1 = role1;
		this.role2 = role2;
		this.superid = superid;
		this.funds = funds;
	}

	
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	public String getRole1() {
		return role1;
	}
	public void setRole1(String role1) {
		this.role1 = role1;
	}
	public String getRole2() {
		return role2;
	}
	public void setRole2(String role2) {
		this.role2 = role2;
	}
	public Integer getSuperid() {
		return superid;
	}
	public void setSuperid(Integer superid) {
		this.superid = superid;
	}
	public Double getFunds() {
		return funds;
	}
	public void setFunds(Double funds) {
		this.funds = funds;
	}
	public String getUsrnm() {
		return usrnm;
	}
	public void setUsrnm(String usrnm) {
		this.usrnm = usrnm;
	}
	public String getPswrd() {
		return pswrd;
	}
	public void setPswrd(String pswrd) {
		this.pswrd = pswrd;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [empid=" + empid + ", fname=" + fname + ", lname=" + lname + ", usrnm=" + usrnm + ", pswrd="
				+ pswrd + ", email=" + email + ", phone=" + phone + ", dept=" + dept + ", role1=" + role1 + ", role2="
				+ role2 + ", superid=" + superid + ", funds=" + funds + "]";
	}


	
	
	
}
