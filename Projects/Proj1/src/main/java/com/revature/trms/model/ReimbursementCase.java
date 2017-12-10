package com.revature.trms.model;

import java.util.Arrays;
import java.util.Date;

public class ReimbursementCase {

	private String case_id;
	private String employeeId;
	private Date event_date;
	private Date request_date;
	private int duration_days;
	private String case_status;
	private String location;
	private String description;
	private double cost;
	private String gradingformat;
	private String eventType;
	private int supervisor_id;
	private double approved_amt;
	private byte[] attachment;

	

	@Override
	public String toString() {
		return "ReimbursementCase [case_id=" + case_id + ", employeeId=" + employeeId + ", event_date=" + event_date
				+ ", request_date=" + request_date + ", duration_days=" + duration_days + ", case_status=" + case_status
				+ ", location=" + location + ", description=" + description + ", cost=" + cost + ", gradingformat="
				+ gradingformat + ", eventType=" + eventType + ", supervisor_id=" + supervisor_id + ", approved_amt="
				+ approved_amt + ", attachment=" + Arrays.toString(attachment) + "]";
	}

	public ReimbursementCase(String employeeId, Date event_date, int duration_days, String location, String eventType,
			 String gradingformat, double cost, String description, int supervisor_id) {
		super();
		this.employeeId = employeeId;
		this.event_date = event_date;
		this.duration_days = duration_days;
		this.location = location;
		this.eventType = eventType;
		this.gradingformat = gradingformat;
		this.cost = cost;
		this.description = description;
		this.supervisor_id = supervisor_id;
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

	public String getCase_status() {
		return case_status;
	}

	public void setCase_status(int case_status) {
		switch (case_status) {
		case 2:
			this.case_status = "Supervisor Approved";
			break;
		case 3:
			this.case_status = "Department Head Approved";
			break;
		case 4:
			this.case_status = "Fully Approved";
			break;
		case 5:
			this.case_status = "Denied";
			break;
		default:
			this.case_status = "Pending";
		}
	}

	public int getSupervisor_id() {
		return supervisor_id;
	}

	public void setSupervisor_id(int supervisor_id) {
		this.supervisor_id = supervisor_id;
	}

	public static int caseStatus2Int(String caseStatus) {
		if (caseStatus.equals("Supervisor Approved")) {
			return 2;
		} else if (caseStatus.equals("Department Head Approved")) {
			return 3;
		} else if (caseStatus.equals("Fully Approved")) {
			return 4;
		} else if (caseStatus.equals("Denied")) {
			return 5;
		} else
			return 1;
	}
	public double getApproved_amt() {
		return approved_amt;
	}

	public void setApproved_amt(double approved_amt) {
		this.approved_amt = approved_amt;
	}
}
