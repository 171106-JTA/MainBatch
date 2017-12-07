package com.revature.bean;

public class ReimbursementForm {
	// User
	private String username;

	// Event Date
	private String year;
	private String month;
	private String day;

	// Event Time
	private String hour;
	private String minute;

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
		return "ReimbursementForm [username=" + username + ", year=" + year + ", month=" + month + ", day=" + day
				+ ", hour=" + hour + ", minute=" + minute + ", description=" + description + ", cost=" + cost
				+ ", gradingFormat=" + gradingFormat + ", eventType=" + eventType + ", street=" + street + ", city="
				+ city + ", state=" + state + ", zip=" + zip + "]";
	}
}
