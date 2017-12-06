package com.trms.obj;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;

public class ReimbRequest {
	private String reqesterLoginId; 
	private int trackingNumber;
	private Date eventDate; 
	private Timestamp eventTime; 
	private String eventName; 
	private String eventType; 
	private String description; 
	private double unmodifiedCost; 
	private String gradingFormat; 
	private String justification; 
	private double workHoursMissing; 
	private boolean supervisorApproved;
	private boolean deptHeadApproved;
	private boolean bennCoApproved;
	private HashMap<String,String> location; 
	
	public ReimbRequest(String reqesterLoginId, int trackingNumber, Date eventDate, Timestamp eventTime,
			String eventName, String eventType, String description, double unmodifiedCost, String gradingFormat,
			String justification, double workHoursMissing, boolean supervisorApproved, boolean deptHeadApproved,
			boolean bennCoApproved) {
		this.reqesterLoginId = reqesterLoginId;
		this.trackingNumber = trackingNumber;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventName = eventName;
		this.eventType = eventType;
		this.description = description;
		this.unmodifiedCost = unmodifiedCost;
		this.gradingFormat = gradingFormat;
		this.justification = justification;
		this.workHoursMissing = workHoursMissing;
		this.supervisorApproved = supervisorApproved;
		this.deptHeadApproved = deptHeadApproved;
		this.bennCoApproved = bennCoApproved;
	} 
	
	public void putLocation(String type, String value) {
		location.put(type, value);
	}
	public HashMap<String,String> getLocation() {
		return location; 
	}

	public String getReqesterLoginId() {
		return reqesterLoginId;
	}

	public void setReqesterLoginId(String reqesterLoginId) {
		this.reqesterLoginId = reqesterLoginId;
	}

	public int getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(int trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Timestamp getEventTime() {
		return eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getUnmodifiedCost() {
		return unmodifiedCost;
	}

	public void setUnmodifiedCost(double unmodifiedCost) {
		this.unmodifiedCost = unmodifiedCost;
	}

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public double getWorkHoursMissing() {
		return workHoursMissing;
	}

	public void setWorkHoursMissing(double workHoursMissing) {
		this.workHoursMissing = workHoursMissing;
	}

	public boolean isSupervisorApproved() {
		return supervisorApproved;
	}

	public void setSupervisorApproved(boolean supervisorApproved) {
		this.supervisorApproved = supervisorApproved;
	}

	public boolean isDeptHeadApproved() {
		return deptHeadApproved;
	}

	public void setDeptHeadApproved(boolean deptHeadApproved) {
		this.deptHeadApproved = deptHeadApproved;
	}

	public boolean isBennCoApproved() {
		return bennCoApproved;
	}

	public void setBennCoApproved(boolean bennCoApproved) {
		this.bennCoApproved = bennCoApproved;
	}
	
	
}
