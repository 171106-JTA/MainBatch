package com.revature.businessobject;

import java.sql.Blob;
import java.sql.Clob;

public class FormAttachment implements BusinessObject {
	private Integer id;
	private Integer formId;
	private Integer typeId;
	private Blob blobAttachment;
	private Clob clobAttachment;
	
	public FormAttachment() {
		// do nothing
	}

	public FormAttachment(Integer id, Integer formId, Integer typeId,
			Blob blobAttachment, Clob clobAttachment) {
		super();
		this.id = id;
		this.formId = formId;
		this.typeId = typeId;
		this.blobAttachment = blobAttachment;
		this.clobAttachment = clobAttachment;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
