package com.revature.trms.model;

import java.util.Arrays;
import java.util.Date;

public class ReimbursementCase {
	
	private String case_id;
	private String employeeId;
	private Date event_date;
	private Date request_date;
	private int duration_days;
	private String location; 
	private String description;
	private double cost;
	private String gradingformat;
	private String eventType;
	private byte[] attachment;
	final long URGENT_TIMESPAN = 7;
	
	
	public boolean caseIsUrgent() {
		Date d1 = this.getEvent_date();
		Date d2 =  this.getRequest_date();
		long diff = Math.abs(d1.getTime() - d2.getTime());
		long diffDays = diff / (24 * 60 * 60 * 1000); // convert milliseconds to days
		return diffDays < URGENT_TIMESPAN ? true : false;
		
	}
	public ReimbursementCase() {
		super();
	}
	public ReimbursementCase(String case_id, String employeeId, Date event_date, Date request_date, int duration_days,
			String location, String description, double cost, String gradingformat, String eventType,
			byte[] attachment) {
		super();
		this.case_id = case_id;
		this.employeeId = employeeId;
		this.event_date = event_date;
		this.request_date = request_date;
		this.duration_days = duration_days;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingformat = gradingformat;
		this.eventType = eventType;
		this.attachment = attachment;
	}
	public ReimbursementCase(String employeeId, Date eventDate, int eventDuration, String eventLocation,
			String eventType, String gradingFormat, double cost, String eventDescription) {
		this.employeeId = employeeId;
		this.event_date = eventDate;
		this.duration_days = eventDuration;
		this.location = eventLocation;
		this.eventType = eventType;
		this.gradingformat = gradingFormat;
		this.cost = cost;
		this.description = eventDescription;
	}
	
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employee) {
		this.employeeId = employee;
	}
	public Date getEvent_date() {
		return event_date;
	}
	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}
	public Date getRequest_date() {
		return request_date;
	}
	public void setRequest_date(Date request_date) {
		this.request_date = request_date;
	}
	public int getDuration_days() {
		return duration_days;
	}
	public void setDuration_days(int duration_days) {
		this.duration_days = duration_days;
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
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	
}
