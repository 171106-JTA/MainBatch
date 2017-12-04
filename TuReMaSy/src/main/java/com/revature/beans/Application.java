package com.revature.beans;

import java.sql.Date;

public class Application {
	private int empID;
	private Integer appID;
	private String empFirst;
	private String empLast;
	private String email;
	private Date moment;
	private String locus;
	private String description;
	private double expense;
	private int grading;
	private int eventType;
	private String justification;
	/*   empID number(3),
    appID number(3),
    empFirst varchar2(20) NOT NULL,
    empLast varchar2(30) NOT NULL,
    moment TIMESTAMP NOT NULL,
    locus varchar2(50) NOT NULL,
    description varchar2(250) NOT NULL,
    expense number(4) NOT NULL,
    grading number(1) NOT NULL,
    eventType varchar2(30) NOT NULL,
    justification varchar2(250) NOT NULL,
   */
	public Application(int empid, String empFirst, String empLast, String email) {
		super();
		this.empID = empID;
		this.empFirst = empFirst;
		this.empLast = empLast;
		this.email = email;	
	}
	
	public Application(int empID, Integer appID, double expense, int eventType, String description, String justification, String empFirst, String empLast, String locus, String email) {
		super();
		this.empID = empID;
		this.appID = appID;
		this.empFirst = empFirst;
		this.empLast = empLast;
		this.moment = moment;
		this.locus = locus;
		this.description = description;
		this.expense = expense;
		this.grading = grading;
		this.eventType = eventType;
		this.justification = justification;
		this.email = email;
	}
	
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEmpid() {
		return empID;
	}

	public void setEmpid(int empID) {
		this.empID = empID;
	}

	public Integer getAppID() {
		return appID;
	}

	public void setAppid(Integer appID) {
		this.appID = appID;
	}

	public String getEmpFirst() {
		return empFirst;
	}

	public void setEmpFirst(String empFirst) {
		this.empFirst = empFirst;
	}

	public String getEmpLast() {
		return empLast;
	}

	public void setEmpLast(String empLast) {
		this.empLast = empLast;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public int getGrading() {
		return grading;
	}

	public void setGrading(int grading) {
		this.grading = grading;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	@Override
	public String toString() {
		return "Application [empID=" + empID + ", appID=" + appID + ", empFirst=" + empFirst + ", empLast=" + empLast
				+ ", email=" + email + ", moment=" + moment + ", locus=" + locus + ", description=" + description
				+ ", expense=" + expense + ", grading=" + grading + ", eventType=" + eventType + ", justification="
				+ justification + "]";
	}	
}