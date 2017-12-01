package com.revature.beans;

import java.sql.Date;

public class Trainer {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String fname;
	private String mname;
	private String lname;
	private Date creation;
	
	public Trainer(String username, String password, String email, String fname, String mname, String lname) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
	}
	
	public Trainer(Integer id, String username, String password, String email, String fname, String mname, String lname,
			Date creation) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.creation = creation;
	}
	public Trainer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
	
	
}
