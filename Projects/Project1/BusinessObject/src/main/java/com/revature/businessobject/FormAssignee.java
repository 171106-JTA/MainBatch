package com.revature.businessobject;

public class FormAssignee implements BusinessObject {
	private Integer id;
	private Integer assigneeId;
	private Integer formId;
	
    public FormAssignee() {
    	// do nothing 
    }

	public FormAssignee(Integer id, Integer assigneeId, Integer formId) {
		super();
		this.id = id;
		this.assigneeId = assigneeId;
		this.formId = formId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}
}
