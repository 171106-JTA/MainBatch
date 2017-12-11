package com.revature.beans;

import java.sql.Date;

public class Event {
	private Integer empid;
	private Integer formid;
	private Integer justification;
	private Integer workHrMissed;
	private Double cost;
	private String status;
	private String location;
	private String type;
	private String description;
	private String gradeFormat;
	private String gradeCutOff;
	private Date submitted;
	private Date occur;
	private Date timeStart;
	private Date timeEnd;

	
	
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event(Integer empid, Integer justification, Integer workHrMissed, Double cost, String status,
			String location, String type, String description, String gradeFormat, String gradeCutOff, Date submitted,
			Date occur, Date timeStart, Date timeEnd) {
		super();
		this.empid = empid;
		this.justification = justification;
		this.workHrMissed = workHrMissed;
		this.cost = cost;
		this.status = status;
		this.location = location;
		this.type = type;
		this.description = description;
		this.gradeFormat = gradeFormat;
		this.gradeCutOff = gradeCutOff;
		this.submitted = submitted;
		this.occur = occur;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}

	public Event(Integer empid, Integer formid, Integer justification, Integer workHrMissed, Double cost, String status,
			String location, String type, String description, String gradeFormat, String gradeCutOff, Date submitted,
			Date occur, Date timeStart, Date timeEnd) {
		super();
		this.empid = empid;
		this.formid = formid;
		this.justification = justification;
		this.workHrMissed = workHrMissed;
		this.cost = cost;
		this.status = status;
		this.location = location;
		this.type = type;
		this.description = description;
		this.gradeFormat = gradeFormat;
		this.gradeCutOff = gradeCutOff;
		this.submitted = submitted;
		this.occur = occur;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}
	
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	public Integer getFormid() {
		return formid;
	}
	public void setFormid(Integer formid) {
		this.formid = formid;
	}
	public Integer getJustification() {
		return justification;
	}
	public void setJustification(Integer justification) {
		this.justification = justification;
	}
	public Integer getWorkHrMissed() {
		return workHrMissed;
	}
	public void setWorkHrMissed(Integer workHrMissed) {
		this.workHrMissed = workHrMissed;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGradeFormat() {
		return gradeFormat;
	}
	public void setGradeFormat(String gradeFormat) {
		this.gradeFormat = gradeFormat;
	}
	public String getGradeCutOff() {
		return gradeCutOff;
	}
	public void setGradeCutOff(String gradeCutOff) {
		this.gradeCutOff = gradeCutOff;
	}
	public Date getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	public Date getOccur() {
		return occur;
	}
	public void setOccur(Date occur) {
		this.occur = occur;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	
}
