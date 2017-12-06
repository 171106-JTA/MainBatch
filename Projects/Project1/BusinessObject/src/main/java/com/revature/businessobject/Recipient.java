package com.revature.businessobject;

public class Recipient implements BusinessObject {
	private Integer id;
	private Integer messageId;
	private Integer recipientId;
	
	public Recipient() {
		// do nothing
	}

	public Recipient(Integer id, Integer messageId, Integer recipientId) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.recipientId = recipientId;
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

	public Integer getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}
}
