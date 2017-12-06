package com.revature.businessobject.view;

import java.util.List;

public class MessageView {
	private String fromUserName;
	private String fromEmail;
	private String priority;
	private String status;
	private String title;
	private List<String> attachments;
	private List<String> recipientUserNames;
	private List<String> recipientEmails;
	
	public MessageView() {
		// do nothing 
	}
	
	public MessageView(String fromUserName, String fromEmail, String priority,
			String status, String title, List<String> attachments,
			List<String> recipientUserNames, List<String> recipientEmails) {
		super();
		this.fromUserName = fromUserName;
		this.fromEmail = fromEmail;
		this.priority = priority;
		this.status = status;
		this.title = title;
		this.attachments = attachments;
		this.recipientUserNames = recipientUserNames;
		this.recipientEmails = recipientEmails;
	}
	
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public List<String> getRecipientUserNames() {
		return recipientUserNames;
	}
	public void setRecipientUserNames(List<String> recipientUserNames) {
		this.recipientUserNames = recipientUserNames;
	}
	public List<String> getRecipientEmails() {
		return recipientEmails;
	}
	public void setRecipientEmails(List<String> recipientEmails) {
		this.recipientEmails = recipientEmails;
	}
}
