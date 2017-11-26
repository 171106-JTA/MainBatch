package com.revature.businessobject;

public class Supervisor implements BusinessObject {
	private Integer id;
	private Integer supervisorId;
	private Integer headSupervisorId;
	private Integer departmentId;
	
	public Supervisor() {
		// do nothing
	}

	public Supervisor(Integer id, Integer supervisorId,
			Integer headSupervisorId, Integer departmentId) {
		super();
		this.id = id;
		this.supervisorId = supervisorId;
		this.headSupervisorId = headSupervisorId;
		this.departmentId = departmentId;
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

	public Integer getHeadSupervisorId() {
		return headSupervisorId;
	}

	public void setHeadSupervisorId(Integer headSupervisorId) {
		this.headSupervisorId = headSupervisorId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
}
