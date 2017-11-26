package com.revature.businessobject;

public class Recipient implements BusinessObject {
	private Integer id;
	private Integer messageId;
	private String recipientEmail;
	
	public Recipient() {
		// do nothing
	}

	public Recipient(Integer id, Integer messageId, String recipientEmail) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.recipientEmail = recipientEmail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
}
