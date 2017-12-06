package com.revature.businessobject;

public class Message implements BusinessObject {
	private Integer id;
	private Integer fromId;
	private Integer messagePriorityId;
	private Integer statusId;
	private String title;
	private String message;
	
	public Message() {
		// do nothing
	}

	public Message(Integer id, Integer fromId, Integer messagePriorityId, Integer statusId,
			String title, String message) {
		super();
		this.id = id;
		this.fromId = fromId;
		this.messagePriorityId = messagePriorityId;
		this.statusId = statusId;
		setTitle(title);
		setMessage(message);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
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
		this.title = BusinessObject.validateString(title);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = BusinessObject.validateString(message);
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
}
