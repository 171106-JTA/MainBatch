package com.revature.beans;

public class Request {
	
	private int id;
	private int employeeId;
	private double cost;
	private int status;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String description;
	private int eventType;
	private int gradingFormat;
	private int daysMissed;
	private String justification;
	private String date;
	
	public Request() {
		super();
	}
	
	
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getGradingFormat() {
		return gradingFormat;
	}
	public void setGradingFormat(int gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	public int getDaysMissed() {
		return daysMissed;
	}
	public void setDaysMissed(int daysMissed) {
		this.daysMissed = daysMissed;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	
	
	
}

