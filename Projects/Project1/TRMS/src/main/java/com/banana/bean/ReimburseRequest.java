package com.banana.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReimburseRequest {
	private int empID;
	private int rrID;
	private String fname;
	private String lname;
	private String location;
	private String description;
	private double cost;
	private int gradingFormat;
	private int eventType;
	private String justification;
	private LocalDateTime eventDate; 
	private String eventName;
	private LocalDate date;
	private String status;
	
	
	public ReimburseRequest(int empID, String fname, String lname, String location, String description, double cost,
			int gradingFormat, int eventType, String justification, LocalDateTime eventDate) {
		super();
		this.empID = empID;
		this.fname = fname;
		this.lname = lname;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.justification = justification;
		this.eventDate = eventDate;
	}
	
	public ReimburseRequest(int rrID, double cost, String eventName, LocalDate date, String status) {
		super();
		this.rrID = rrID;
		this.cost = cost;
		this.eventName = eventName;
		this.date = date;
		this.status = status;
	}
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEventName() {
		return eventName;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getRrID() {
		return rrID;
	}

	public void setRrID(int rrID) {
		this.rrID = rrID;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(int gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	
	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}
	
	
}
