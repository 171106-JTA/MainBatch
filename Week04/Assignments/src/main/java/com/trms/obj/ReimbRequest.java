package com.trms.obj;


public class ReimbRequest {
	private String requesterLoginId; 
	private int trackingNumber;
	private String eventDate; 
	private String eventTime; 
	private String eventName; 
	private String eventType; 
	private String description; 
	private double unmodifiedCost; 
	private String gradingFormat; 
	private String justification; 
	private String workHoursMissing; 
	private boolean supervisorApproved;
	private boolean deptHeadApproved;
	private boolean bennCoApproved;
	private String location; 
	
	public ReimbRequest(String requesterLoginId, int trackingNumber, String eventDate, String eventTime,
			String eventName, String eventType, String description, double unmodifiedCost, String gradingFormat,
			String justification, String workHoursMissing, boolean supervisorApproved, boolean deptHeadApproved,
			boolean bennCoApproved) {
		this.requesterLoginId = requesterLoginId;
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
	public ReimbRequest(String requesterLoginId, String eventDate, String eventTime, String eventName, 
			String eventType, String description, 
			String justification, double cost, String workHoursToMiss) {
		this.trackingNumber = 0; 
		this.requesterLoginId = requesterLoginId;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventName = eventName;
		this.eventType = eventType;
		this.description = description;
		this.justification = justification;
		this.unmodifiedCost = cost;
		this.workHoursMissing = workHoursToMiss;
		this.supervisorApproved = false;
		this.deptHeadApproved = false;
		this.bennCoApproved = false;	}
	
	public String getLocation() {
		return location; 
	}
	public void setLocation(String location) {
		this.location = location; 
	}
	public String getReqesterLoginId() {
		return requesterLoginId;
	}

	public void setReqesterLoginId(String requesterLoginId) {
		this.requesterLoginId = requesterLoginId;
	}

	public int getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(int trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
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

	public String getWorkHoursMissing() {
		return workHoursMissing;
	}

	public void setWorkHoursMissing(String workHoursMissing) {
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
