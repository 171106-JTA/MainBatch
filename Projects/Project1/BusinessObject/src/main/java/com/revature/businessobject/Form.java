package com.revature.businessobject;

import java.sql.Date;

public class Form implements BusinessObject {
	private Integer id;
	private Integer userId;
	private Integer departmentId;
	private Integer eventStateCityId;
	private Integer gradingTypeId;
	private Integer reimbursementRateId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String email;
	private Date eventDateTimeStart;
	private Date eventDateTimeEnd;
	private Float eventCost;
	private String description;
	private Date timeOffBegin;
	private Date timeOffEnd;
	
	public Form() {
		// do nothing
	}

	public Form(Integer id, Integer userId, Integer departmentId,
			Integer eventStateCityId, Integer gradingTypeId, Integer reimbursementRateId,
			String firstName, String lastName, String phoneNumber, String address, String email,
			Date eventDateTimeStart, Date eventDateTimeEnd, Float eventCost, String description,
			Date timeOffBegin, Date timeOffEnd) {
		super();
		this.id = id;
		this.userId = userId;
		this.departmentId = departmentId;
		this.eventStateCityId = eventStateCityId;
		this.gradingTypeId = gradingTypeId;
		this.reimbursementRateId = reimbursementRateId;
		this.eventDateTimeStart = eventDateTimeStart;
		this.eventDateTimeEnd = eventDateTimeEnd;
		this.eventCost = eventCost;
		this.timeOffBegin = timeOffBegin;
		this.timeOffEnd = timeOffEnd;
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setAddress(address);
		setEmail(email);	
		setDescription(description);
	}

	public Integer getGradingTypeId() {
		return gradingTypeId;
	}

	public void setGradingTypeId(Integer gradingTypeId) {
		this.gradingTypeId = gradingTypeId;
	}

	public Integer getReimbursementRateId() {
		return reimbursementRateId;
	}

	public void setReimbursementRateId(Integer reimbursementRateId) {
		this.reimbursementRateId = reimbursementRateId;
	}

	public Date getEventDateTimeStart() {
		return eventDateTimeStart;
	}

	public void setEventDateTimeStart(Date eventDateTimeStart) {
		this.eventDateTimeStart = eventDateTimeStart;
	}

	public Date getEventDateTimeEnd() {
		return eventDateTimeEnd;
	}

	public void setEventDateTimeEnd(Date eventDateTimeEnd) {
		this.eventDateTimeEnd = eventDateTimeEnd;
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
		this.firstName = BusinessObject.validateString(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = BusinessObject.validateString(lastName);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = BusinessObject.validateString(phoneNumber);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = BusinessObject.validateString(address);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = BusinessObject.validateString(email);
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
		this.description = BusinessObject.validateString(description);
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
