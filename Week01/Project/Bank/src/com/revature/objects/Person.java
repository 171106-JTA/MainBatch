/**
 * Class Person
 * It is the root table for all the actors in the system.
 * It provides properties and and other methods inherited 
 * by the customer class.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */
package com.revature.objects;

import java.io.Serializable;
import java.util.Date;

import com.revature.display.MyDisplays;

public class Person implements Serializable {
	/*
	 * Basic object to store all the actors
	 * */
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = -8997628782186425935L;
	private static final long serialVersionUID = 6957627302582870048L;

	//Properties
	private int id;			//auto increment
	private String name;
	private String address;
	private String email;
	private String phone;
	private String ssn;
	private Date dob;
	protected static int nbOfPersons;

	
	//Constructor
	public Person(String name, String address, String email, String phone, String ssn, Date dob) {
		this.id = ++nbOfPersons;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.ssn = ssn;
		this.dob = dob;
	}
	
	public Person() {
		
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return address;
	}
	public void setAdress(String adress) {
		this.address = adress;
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

	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Person \n----------\nid:\t" + id + "\nname\t" + name + 
				"\nadress\t" + address + "\nemail\t" + email + "\nphone:\t" + 
				phone + "\nSSN:\t" + ssn + "\ndob:\t" + new MyDisplays<>().formatDate(dob, "MM/dd/yyyy") + "\n----------";
	}
	
}
