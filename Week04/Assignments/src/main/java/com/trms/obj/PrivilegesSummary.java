package com.trms.obj;

public class PrivilegesSummary {
	private String firstName; 
	private String lastName; 
	private boolean isSupervisor;
	private boolean isDeptHead;
	private String department;
	private boolean isBenCo;
	
	public PrivilegesSummary(String firstName, String lastName, boolean isSupervisor,
			boolean isDeptHead, String department, boolean isBenCo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.isSupervisor = isSupervisor;
		this.isDeptHead = isDeptHead;
		this.department = department;
		this.isBenCo = isBenCo;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public boolean isSupervisor() {
		return isSupervisor;
	}

	public boolean isDeptHead() {
		return isDeptHead;
	}

	public String getDepartment() {
		return department;
	}

	public boolean isBenCo() {
		return isBenCo;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public void setDeptHead(boolean isDeptHead) {
		this.isDeptHead = isDeptHead;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setBenCo(boolean isBenCo) {
		this.isBenCo = isBenCo;
	}
	
	

}
