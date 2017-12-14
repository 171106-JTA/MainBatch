package com.revature.beans;

import java.io.InputStream;
import java.sql.Date;

public class Request {
	private String event, location, description, gradingFormat;
	private double cost;
	private Date submissionDate, dateOfEvent;
	private InputStream inputStream;
	private int id;
	private Employee emp;
	private String status;
	
	/**
	 * A constructor used when a user submits a request.
	 * The submission date is excluded in the parameters as well as the request id excluded.
	 * @param empUsername
	 * @param event
	 * @param location
	 * @param description
	 * @param cost
	 * @param gradingFormat
	 * @param dateOfEvent
	 * @param inputStream
	 */
	public Request(Employee emp, String event, String location, String description,
			double cost, String gradingFormat, Date dateOfEvent, InputStream inputStream, String status) {
		this.event = event;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.dateOfEvent = dateOfEvent;
		this.inputStream = inputStream;
		this.status = status;
		this.emp = emp;
	}
	
	/**
	 * 
	 * @param id
	 * @param emp
	 * @param event
	 * @param location
	 * @param description
	 * @param cost
	 * @param gradingFormat
	 * @param submissionDate
	 * @param dateOfEvent
	 * @param inputStream
	 */
	public Request(int id, Employee emp, String event, String location, String description,
			double cost, String gradingFormat, Date submissionDate, Date dateOfEvent, InputStream inputStream) {
		this.id = id;
		this.emp = emp;
		this.event = event;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.submissionDate = submissionDate;
		this.dateOfEvent = dateOfEvent;
		this.inputStream = inputStream;
	}
	
	/**
	 * A constructor with all parameters. All field data will be set.
	 * Used when the DAO pulls requests from the DB and stores all data in a request bean.
	 * @param id
	 * @param empUsername
	 * @param event
	 * @param location
	 * @param description
	 * @param cost
	 * @param gradingFormat
	 * @param submissionDate
	 * @param dateOfEvent
	 * @param inputStream
	 */
	public Request(int id, Employee emp, String event, String location, String description,
			double cost, String gradingFormat, Date submissionDate, Date dateOfEvent, InputStream inputStream, String status) {
		this.event = event;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.dateOfEvent = dateOfEvent;
		this.inputStream = inputStream;
		this.id = id;
		this.submissionDate = submissionDate;
		this.status = status;
		this.emp = emp;
	}
	
	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
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

	public String getGradingFormat() {
		return gradingFormat;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Request [event=" + event + ", location=" + location + ", description="
				+ description + ", gradingFormat=" + gradingFormat + ", cost=" + cost + ", submissionDate="
				+ submissionDate + ", dateOfEvent=" + dateOfEvent + ", inputStream=" + inputStream + "]";
	}
}