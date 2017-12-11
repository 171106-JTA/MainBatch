package com.revature.businessobject.view;

import java.sql.Date;
import java.util.List;

public class FormView implements BusinessObjectView {
	private Integer formId;
	private Integer userId;
	private String department;
	private String state;
	private String city;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String description;
	private String address;
	private String gradingType;
	private String reimbursementRate;
	private String status;
	private Float eventCost;
	private String eventDateTimeStart;
	private String eventDateTimeEnd;
	private String timeOffBegin;
	private String timeOffEnd;
	private List<String> gradeAttachments;
	private List<String> otherAttachments;
	private List<Integer> gradeAttachmentIds;
	private List<Integer> otherAttachmentIds;
	
	public FormView() {
		// do nothing
	}

	public FormView(Integer formId, Integer userId, String department,
			String state, String city, String firstName, String lastName,
			String email, String phoneNumber, String description, String status,
			String address, String gradingType, String reimbursementRate,
			Float eventCost, String eventDateTimeStart, String eventDateTimeEnd,
			String timeOffBegin, String timeOffEnd, List<String> gradeAttachments,
			List<String> otherAttachments, List<Integer> gradeAttachmentIds,
			List<Integer> otherAttachmentIds) {
		super();
		this.formId = formId;
		this.userId = userId;
		this.department = department;
		this.state = state;
		this.city = city;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.address = address;
		this.gradingType = gradingType;
		this.reimbursementRate = reimbursementRate;
		this.status = status;
		this.eventCost = eventCost;
		this.eventDateTimeStart = eventDateTimeStart;
		this.eventDateTimeEnd = eventDateTimeEnd;
		this.timeOffBegin = timeOffBegin;
		this.timeOffEnd = timeOffEnd;
		this.gradeAttachments = gradeAttachments;
		this.otherAttachments = otherAttachments;
		this.gradeAttachmentIds = gradeAttachmentIds;
		this.otherAttachmentIds = otherAttachmentIds;
	}



	public List<Integer> getGradeAttachmentIds() {
		return gradeAttachmentIds;
	}

	public void setGradeAttachmentIds(List<Integer> gradeAttachmentIds) {
		this.gradeAttachmentIds = gradeAttachmentIds;
	}

	public List<Integer> getOtherAttachmentIds() {
		return otherAttachmentIds;
	}

	public void setOtherAttachmentIds(List<Integer> otherAttachmentIds) {
		this.otherAttachmentIds = otherAttachmentIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGradingType() {
		return gradingType;
	}

	public void setGradingType(String gradingType) {
		this.gradingType = gradingType;
	}

	public String getReimbursementRate() {
		return reimbursementRate;
	}

	public void setReimbursementRate(String reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getEventCost() {
		return eventCost;
	}

	public void setEventCost(Float eventCost) {
		this.eventCost = eventCost;
	}

	public String getEventDateTimeStart() {
		return eventDateTimeStart;
	}

	public void setEventDateTimeStart(String eventDateTimeStart) {
		this.eventDateTimeStart = eventDateTimeStart;
	}

	public String getEventDateTimeEnd() {
		return eventDateTimeEnd;
	}

	public void setEventDateTimeEnd(String eventDateTimeEnd) {
		this.eventDateTimeEnd = eventDateTimeEnd;
	}

	public String getTimeOffBegin() {
		return timeOffBegin;
	}

	public void setTimeOffBegin(String timeOffBegin) {
		this.timeOffBegin = timeOffBegin;
	}

	public String getTimeOffEnd() {
		return timeOffEnd;
	}

	public void setTimeOffEnd(String timeOffEnd) {
		this.timeOffEnd = timeOffEnd;
	}

	public List<String> getGradeAttachments() {
		return gradeAttachments;
	}

	public void setGradeAttachments(List<String> gradeAttachments) {
		this.gradeAttachments = gradeAttachments;
	}

	public List<String> getOtherAttachments() {
		return otherAttachments;
	}

	public void setOtherAttachments(List<String> otherAttachments) {
		this.otherAttachments = otherAttachments;
	}
}
