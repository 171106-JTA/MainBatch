package com.revature.beans;

public class Message {
	private Integer formId;
	private Integer adminId;
	private String msg;
	
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(Integer formId, Integer adminId, String msg) {
		super();
		this.formId = formId;
		this.adminId = adminId;
		this.msg = msg;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
