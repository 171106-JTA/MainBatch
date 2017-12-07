package com.revature.beans;

import java.io.InputStream;
import java.sql.Date;

public class Request {
	private String username, event, location, description, gradingFormat;
	private double cost;
	private Date submissionDate, dateOfEvent;
	private InputStream inputStream;
	
	public Request(String empUsername, String event, String location, String description,
			double cost, String gradingFormat, Date dateOfEvent, InputStream inputStream) {
		this.username = empUsername;
		this.event = event;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.dateOfEvent = dateOfEvent;
		this.inputStream = inputStream;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "Request [username=" + username + ", event=" + event + ", location=" + location + ", description="
				+ description + ", gradingFormat=" + gradingFormat + ", cost=" + cost + ", submissionDate="
				+ submissionDate + ", dateOfEvent=" + dateOfEvent + ", inputStream=" + inputStream + "]";
	}
}