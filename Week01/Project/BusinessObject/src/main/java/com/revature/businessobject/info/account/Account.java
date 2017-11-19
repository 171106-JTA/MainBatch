package com.revature.businessobject.info.account;

import java.util.Date;

import com.revature.businessobject.info.Info;

public class Account extends Info {
	public static final String NUMBER = "account_number";
	public static final String TYPEID = "type_id";
	public static final String STATUSID = "status_id";
	public static final String CREATED = "created";
	
	private String number;
	private String created;
	private String type;
	private long typeId;
	private long statusId;
	
	/**
	 * Initialize basic account data 
	 * @param userId user unique identifier (foreign key)
	 * @param number account number 
	 * @param type what kind of account is it
	 * @see Type 
	 */
	public Account(long userId, String number, long typeId, long statusId, String created, String type) {
		super(userId);
		this.number = number;
		this.typeId = typeId;
		this.statusId = statusId;
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
}
