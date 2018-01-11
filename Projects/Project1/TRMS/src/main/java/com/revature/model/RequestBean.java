package com.revature.model;

public class RequestBean {
	private int id;
	private String type;
	private double amount;
	private int eventId;
	private int empId;
	private int supervisor;
	private String status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RequestBean(int id, String type, double amount, int eventId, int empId, int supervisor, String status) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.eventId = eventId;
		this.empId = empId;
		this.supervisor = supervisor;
		this.status = status;
	}
}
