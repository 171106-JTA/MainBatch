package com.revature.bean;

public class ReimbursementForm {
	private int reimbursementID;

	// User
	private String username;

	// Event Date
	private String year;
	private String month;
	private String day;

	// Event Time
	private String hour;
	private String minute;

	// Date of Form submission
	private String submitYear;
	private String submitMonth;
	private String submitDay;

	// Time of Form submission
	private String submitHour;
	private String submitMinute;

	// Event info
	private String description;
	private String cost;
	private String gradingFormat;
	private String eventType;

	// Address
	private String street;
	private String city;
	private String state;
	private String zip;

	private String workRelatedJustification;
	private int passingGrade;

	private int status;
	private int exceedsFundsFlag;
	private String reason_for_excess_funds;
	private String reason_for_denial;
	private int approvalByDirectSupervisor;
	private int approvalByDepartmentHead;
	private int approvalByBenCo;
	private int urgency;

	//Legacy constructor
	public ReimbursementForm(String username, String year, String month, String day, String hour, String minute,
			String description, String cost, String gradingFormat, String eventType, String street, String city,
			String state, String zip) {
		super();
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	//Constructor with default values
	public ReimbursementForm(String username, String year, String month, String day, String hour, String minute,
			String submitYear, String submitMonth, String submitDay, String submitHour, String submitMinute,
			String description, String cost, String gradingFormat, String eventType, String street, String city,
			String state, String zip, String workRelatedJustification, int passingGrade, int urgency) {
		super();
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.submitYear = submitYear;
		this.submitMonth = submitMonth;
		this.submitDay = submitDay;
		this.submitHour = submitHour;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.workRelatedJustification = workRelatedJustification;
		this.passingGrade = passingGrade;

		// Default values
		this.status = 0;
		this.exceedsFundsFlag = 0;
		this.reason_for_excess_funds = null;
		this.reason_for_denial = null;
		this.approvalByDirectSupervisor = 0;
		this.approvalByDepartmentHead = 0;
		this.approvalByBenCo = 0;

		this.urgency = urgency;
	}
	
	//All feilds except reimbursementID
	public ReimbursementForm(String username, String year, String month, String day, String hour, String minute,
			String submitYear, String submitMonth, String submitDay, String submitHour, String submitMinute,
			String description, String cost, String gradingFormat, String eventType, String street, String city,
			String state, String zip, String workRelatedJustification, int passingGrade, int status,
			int exceedsFundsFlag, String reason_for_excess_funds, String reason_for_denial,
			int approvalByDirectSupervisor, int approvalByDepartmentHead, int approvalByBenCo, int urgency) {
		super();
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.submitYear = submitYear;
		this.submitMonth = submitMonth;
		this.submitDay = submitDay;
		this.submitHour = submitHour;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.workRelatedJustification = workRelatedJustification;
		this.passingGrade = passingGrade;
		this.status = status;
		this.exceedsFundsFlag = exceedsFundsFlag;
		this.reason_for_excess_funds = reason_for_excess_funds;
		this.reason_for_denial = reason_for_denial;
		this.approvalByDirectSupervisor = approvalByDirectSupervisor;
		this.approvalByDepartmentHead = approvalByDepartmentHead;
		this.approvalByBenCo = approvalByBenCo;
		this.urgency = urgency;
	}
	
	//All Fields
	public ReimbursementForm(int reimbursementID, String username, String year, String month, String day, String hour,
			String minute, String submitYear, String submitMonth, String submitDay, String submitHour,
			String submitMinute, String description, String cost, String gradingFormat, String eventType, String street,
			String city, String state, String zip, String workRelatedJustification, int passingGrade, int status,
			int exceedsFundsFlag, String reason_for_excess_funds, String reason_for_denial,
			int approvalByDirectSupervisor, int approvalByDepartmentHead, int approvalByBenCo, int urgency) {
		super();
		this.reimbursementID = reimbursementID;
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.submitYear = submitYear;
		this.submitMonth = submitMonth;
		this.submitDay = submitDay;
		this.submitHour = submitHour;
		this.submitMinute = submitMinute;
		this.description = description;
		this.cost = cost;
		this.gradingFormat = gradingFormat;
		this.eventType = eventType;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.workRelatedJustification = workRelatedJustification;
		this.passingGrade = passingGrade;
		this.status = status;
		this.exceedsFundsFlag = exceedsFundsFlag;
		this.reason_for_excess_funds = reason_for_excess_funds;
		this.reason_for_denial = reason_for_denial;
		this.approvalByDirectSupervisor = approvalByDirectSupervisor;
		this.approvalByDepartmentHead = approvalByDepartmentHead;
		this.approvalByBenCo = approvalByBenCo;
		this.urgency = urgency;
	}

	public int getReimbursementID() {
		return reimbursementID;
	}

	public void setReimbursementID(int reimbursementID) {
		this.reimbursementID = reimbursementID;
	}

	public String getSubmitYear() {
		return submitYear;
	}

	public void setSubmitYear(String submitYear) {
		this.submitYear = submitYear;
	}

	public String getSubmitMonth() {
		return submitMonth;
	}

	public void setSubmitMonth(String submitMonth) {
		this.submitMonth = submitMonth;
	}

	public String getSubmitDay() {
		return submitDay;
	}

	public void setSubmitDay(String submitDay) {
		this.submitDay = submitDay;
	}

	public String getSubmitHour() {
		return submitHour;
	}

	public void setSubmitHour(String submitHour) {
		this.submitHour = submitHour;
	}
	
	public String getSubmitMinute() {
		return submitMinute;
	}

	public void setSubmitMinute(String submitMinute) {
		this.submitMinute = submitMinute;
	}

	public String getWorkRelatedJustification() {
		return workRelatedJustification;
	}

	public void setWorkRelatedJustification(String workRelatedJustification) {
		this.workRelatedJustification = workRelatedJustification;
	}

	public int getPassingGrade() {
		return passingGrade;
	}

	public void setPassingGrade(int passingGrade) {
		this.passingGrade = passingGrade;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getExceedsFundsFlag() {
		return exceedsFundsFlag;
	}

	public void setExceedsFundsFlag(int exceedsFundsFlag) {
		this.exceedsFundsFlag = exceedsFundsFlag;
	}

	public String getReason_for_excess_funds() {
		return reason_for_excess_funds;
	}

	public void setReason_for_excess_funds(String reason_for_excess_funds) {
		this.reason_for_excess_funds = reason_for_excess_funds;
	}

	public String getReason_for_denial() {
		return reason_for_denial;
	}

	public void setReason_for_denial(String reason_for_denial) {
		this.reason_for_denial = reason_for_denial;
	}

	public int getApprovalByDirectSupervisor() {
		return approvalByDirectSupervisor;
	}

	public void setApprovalByDirectSupervisor(int approvalByDirectSupervisor) {
		this.approvalByDirectSupervisor = approvalByDirectSupervisor;
	}

	public int getApprovalByDepartmentHead() {
		return approvalByDepartmentHead;
	}

	public void setApprovalByDepartmentHead(int approvalByDepartmentHead) {
		this.approvalByDepartmentHead = approvalByDepartmentHead;
	}

	public int getApprovalByBenCo() {
		return approvalByBenCo;
	}

	public void setApprovalByBenCo(int approvalByBenCo) {
		this.approvalByBenCo = approvalByBenCo;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
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

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String streets) {
		this.street = streets;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ReimbursementForm [reimbursementID=" + reimbursementID + ", username=" + username + ", year=" + year
				+ ", month=" + month + ", day=" + day + ", hour=" + hour + ", minute=" + minute + ", submitYear="
				+ submitYear + ", submitMonth=" + submitMonth + ", submitDay=" + submitDay + ", submitHour="
				+ submitHour + ", submitMinute=" + submitMinute + ", description=" + description + ", cost=" + cost
				+ ", gradingFormat=" + gradingFormat + ", eventType=" + eventType + ", street=" + street + ", city="
				+ city + ", state=" + state + ", zip=" + zip + ", workRelatedJustification=" + workRelatedJustification
				+ ", passingGrade=" + passingGrade + ", status=" + status + ", exceedsFundsFlag=" + exceedsFundsFlag
				+ ", reason_for_excess_funds=" + reason_for_excess_funds + ", reason_for_denial=" + reason_for_denial
				+ ", approvalByDirectSupervisor=" + approvalByDirectSupervisor + ", approvalByDepartmentHead="
				+ approvalByDepartmentHead + ", approvalByBenCo=" + approvalByBenCo + ", urgency=" + urgency + "]";
	}

//	@Override
//	public String toString() {
//		return "ReimbursementForm [username=" + username + ", year=" + year + ", month=" + month + ", day=" + day
//				+ ", hour=" + hour + ", minute=" + minute + ", description=" + description + ", cost=" + cost
//				+ ", gradingFormat=" + gradingFormat + ", eventType=" + eventType + ", street=" + street + ", city="
//				+ city + ", state=" + state + ", zip=" + zip + "]";
//	}
	
	

	
}
