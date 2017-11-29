package com.revature.businessobject;

public class BenefitCoordinator implements BusinessObject {
	private Integer id;
	private Integer bencoId;
	private Integer departmentId;
	
	public BenefitCoordinator() {
		// do nothing
	}
	
	public BenefitCoordinator(Integer id, Integer bencoId,
			Integer departmentId) {
		super();
		this.id = id;
		this.bencoId = bencoId;
		this.departmentId = departmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBencoId() {
		return bencoId;
	}

	public void setBencoId(Integer bencoId) {
		this.bencoId = bencoId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
}
