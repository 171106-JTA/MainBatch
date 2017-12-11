package com.revature.businessobject;

import java.sql.Date;

public class FormStatus implements BusinessObject {
	private Integer id;
	private Integer formId;
	private Integer processedBy;
	private Integer statusId;
	private Float reimbursement;
	private String description;
	private Date timestamp;
	
	public FormStatus() {
		// do nothing
	}

	public FormStatus(Integer id, Integer formId, Integer processedBy,
			Integer statusId, Float reimbursement, String description, Date timestamp) {
		super();
		this.id = id;
		this.formId = formId;
		this.processedBy = processedBy;
		this.statusId = statusId;
		this.reimbursement = reimbursement;
		this.timestamp = timestamp;
		setDescription(description);
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(Integer processedBy) {
		this.processedBy = processedBy;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Float getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(Float reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = BusinessObject.validateString(description);
	}
}
