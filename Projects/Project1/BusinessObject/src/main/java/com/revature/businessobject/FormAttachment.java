package com.revature.businessobject;

import java.sql.Blob;
import java.sql.Clob;

public class FormAttachment implements BusinessObject {
	private Integer id;
	private Integer formId;
	private String attachmentType;
	private String attachmentName;
	private Blob blobAttachment;
	private Clob clobAttachment;
	
	public FormAttachment() {
		// do nothing
	}

	public FormAttachment(Integer id, Integer formId, String attachmentType,
			String attachmentName, Blob blobAttachment, Clob clobAttachment) {
		super();
		this.id = id;
		this.formId = formId;
		this.attachmentType = attachmentType;
		this.attachmentName = attachmentName;
		this.blobAttachment = blobAttachment;
		this.clobAttachment = clobAttachment;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentName() {
		return this.attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
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
