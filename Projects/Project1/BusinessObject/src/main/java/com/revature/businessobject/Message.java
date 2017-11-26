package com.revature.businessobject;

public class Message implements BusinessObject {
	private Integer id;
	private Integer formId;
	private Integer messagePriorityId;
	private String title;
	private String message;
	
	public Message() {
		// do nothing
	}

	public Message(Integer id, Integer formId, Integer messagePriorityId,
			String title, String message) {
		super();
		this.id = id;
		this.formId = formId;
		this.messagePriorityId = messagePriorityId;
		this.title = title;
		this.message = message;
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

	public Integer getMessagePriorityId() {
		return messagePriorityId;
	}

	public void setMessagePriorityId(Integer messagePriorityId) {
		this.messagePriorityId = messagePriorityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
