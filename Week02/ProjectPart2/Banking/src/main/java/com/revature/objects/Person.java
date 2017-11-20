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

import java.util.Date;

import com.revature.generalmethods.GeneralFunctions;

public class Person {
	/*
	 * Basic object to store all the actors
	 * */
	
	/**
	 * 
	 */

	//Properties
	private int personID;			//auto increment
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String areacode;
	private String country = "USA";
	private String email;
	private String phone;
	private String ssn;
	private Date dob;

	protected static int nbOfPersons;

	
	//Constructor
	public Person(int personid, String fname, String lname, String address, String city, String state, String areacode, String country, String email, String phone, String ssn, Date dob) {
		this.personID = personid;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.email = email;
		this.phone = phone;
		this.ssn = ssn;
		this.dob = dob;
	}
	public Person(String fname, String lname, String address, String city, String state, String areacode, String country, String email, String phone, String ssn, Date dob) {
		//this.personID = id;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.email = email;
		this.phone = phone;
		this.ssn = ssn;
		this.dob = dob;
	}
	
	public Person() {
		
	}

	//Getters and Setters
	public int getId() {
		return personID;
	}

	public String getFName() {
		return fname;
	}
	public void setFName(String fname) {
		this.fname = fname;
	}

	public String getLName() {
		return lname;
	}
	public void setLName(String lname) {
		this.lname = lname;
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

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Person \n----------\nid:\t" + personID + "\nfirstname\t" + fname + "\nlaststname\t" + lname + 
				"\nadress\t" + address + "\ncity\t" + city + "\nstate\t" + state + "\nareacode\t" + areacode + "\nemail\t" + email + "\nphone:\t" + 
				phone + "\nSSN:\t" + ssn + "\ndob:\t" + new GeneralFunctions<Integer>().formatDate(dob, "MM/dd/yyyy") + "\n----------";
	}

	
}
