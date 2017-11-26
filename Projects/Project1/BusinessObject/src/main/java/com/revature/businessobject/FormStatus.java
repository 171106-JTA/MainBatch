package com.revature.businessobject;

public class FormStatus implements BusinessObject {
	private Integer id;
	private Integer formId;
	private Integer processedBy;
	private Integer statusId;
	private Float originalEstimate;
	private Float reimbursement;
	private String description;
	
	public FormStatus() {
		// do nothing
	}

	public FormStatus(Integer id, Integer formId, Integer processedBy,
			Integer statusId, Float originalEstimate, Float reimbursement,
			String description) {
		super();
		this.id = id;
		this.formId = formId;
		this.processedBy = processedBy;
		this.statusId = statusId;
		this.originalEstimate = originalEstimate;
		this.reimbursement = reimbursement;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Float getOriginalEstimate() {
		return originalEstimate;
	}

	public void setOriginalEstimate(Float originalEstimate) {
		this.originalEstimate = originalEstimate;
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
		this.description = description;
	}
}
