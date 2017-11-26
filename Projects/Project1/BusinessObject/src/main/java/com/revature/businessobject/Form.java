package com.revature.businessobject;

import java.sql.Date;

public class Form implements BusinessObject {
	private Integer id;
	private Integer userId;
	private Integer departmentId;
	private Integer eventStateCityId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String email;
	private Date eventDateTime;
	private Float eventCost;
	private String description;
	private Date timeOffBegin;
	private Date timeOffEnd;
	
	public Form() {
		// do nothing
	}

	public Form(Integer id, Integer userId, Integer departmentId,
			Integer eventStateCityId, String firstName, String lastName,
			String phoneNumber, String address, String email,
			Date eventDateTime, Float eventCost, String description,
			Date timeOffBegin, Date timeOffEnd) {
		super();
		this.id = id;
		this.userId = userId;
		this.departmentId = departmentId;
		this.eventStateCityId = eventStateCityId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
		this.eventDateTime = eventDateTime;
		this.eventCost = eventCost;
		this.description = description;
		this.timeOffBegin = timeOffBegin;
		this.timeOffEnd = timeOffEnd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getEventStateCityId() {
		return eventStateCityId;
	}

	public void setEventStateCityId(Integer eventStateCityId) {
		this.eventStateCityId = eventStateCityId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEventDateTime() {
		return eventDateTime;
	}

	public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	public Float getEventCost() {
		return eventCost;
	}

	public void setEventCost(Float eventCost) {
		this.eventCost = eventCost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeOffBegin() {
		return timeOffBegin;
	}

	public void setTimeOffBegin(Date timeOffBegin) {
		this.timeOffBegin = timeOffBegin;
	}

	public Date getTimeOffEnd() {
		return timeOffEnd;
	}

	public void setTimeOffEnd(Date timeOffEnd) {
		this.timeOffEnd = timeOffEnd;
	}
}
