package com.revature.businessobject;

import java.sql.Blob;
import java.sql.Clob;

public class MessageAttachment implements BusinessObject {
	private Integer id;
	private Integer messageId;
	private Blob blobAttachment;
	private Clob clobAttachment;
	
	public MessageAttachment() {
		// do nothing
	}

	public MessageAttachment(Integer id, Integer messageId, Blob blobAttachment,
			Clob clobAttachment) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.blobAttachment = blobAttachment;
		this.clobAttachment = clobAttachment;
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

	public Blob getBlobAttachment() {
		return blobAttachment;
	}

	public void setBlobAttachment(Blob blobAttachment) {
		this.blobAttachment = blobAttachment;
	}

	public Clob getClobAttachment() {
		return clobAttachment;
	}

	public void setClobAttachment(Clob clobAttachment) {
		this.clobAttachment = clobAttachment;
	}
}
