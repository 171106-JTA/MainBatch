package com.revature.trms.model;

import java.util.Date;

public class Case {
	
	private String case_id;
	private Employee employee;
	private Date date;
	private Date time;
	private String location; 
	private String description;
	private String cost;
	private String gradingformat;
	private String eventType;
	
	
	
	public Case(Employee employee, Date date, Date time, String location, String description,
			String cost, String gradingformat, String eventType) {
		super();
		this.employee = employee;
		this.date = date;
		this.time = time;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingformat = gradingformat;
		this.eventType = eventType;
	}
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getGradingformat() {
		return gradingformat;
	}
	public void setGradingformat(String gradingformat) {
		this.gradingformat = gradingformat;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public byte getFiles() {
		return files;
	}
	public void setFiles(byte files) {
		this.files = files;
	}
	private byte files;
	
}
