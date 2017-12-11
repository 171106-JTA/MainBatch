package com.revature.businessobject.view;

public class FormStatusView implements BusinessObjectView {
	private String processedBy;
	private String status;
	private Float reimbursement;
	private String description;
	private String timestamp;
	
	
	public FormStatusView() {
		// do nothing
	}
	
	
	public FormStatusView(String processedBy, String status,
			Float reimbursement, String description, String timestamp) {
		super();
		this.processedBy = processedBy;
		this.status = status;
		this.reimbursement = reimbursement;
		this.description = description;
		this.timestamp = timestamp;
	}
	public String getProcessedBy() {
		return processedBy;
	}
	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
