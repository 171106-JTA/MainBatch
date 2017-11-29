package com.revature.businessobject;

public class Supervisor implements BusinessObject {
	private Integer id;
	private Integer bencoId;
	private Integer supervisorId;
	
	public Supervisor() {
		// do nothing
	}


	public Supervisor(Integer id, Integer bencoId, Integer supervisorId) {
		super();
		this.id = id;
		this.bencoId = bencoId;
		this.supervisorId = supervisorId;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}


	public Integer getBencoId() {
		return bencoId;
	}


	public void setBencoId(Integer bencoId) {
		this.bencoId = bencoId;
	}
	
	
	
}
