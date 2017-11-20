package com.revature.businessobject.info.account;

import java.util.Date;

import com.revature.businessobject.info.Info;

public class Account extends Info {
	public static final String NUMBER = "account_number";
	public static final String TYPEID = "type_id";
	public static final String STATUSID = "status_id";
	public static final String CREATED = "created";
	public static final String BALANCE = "balance";
	
	
	private String number;
	private String created;
	private String type;
	private String status;
	private float balance;
	private long typeId;
	private long statusId;
	
	/**
	 * Initialize basic account data 
	 * @param userId user unique identifier (foreign key)
	 * @param number account number 
	 * @param type what kind of account is it
	 * @see Type 
	 */
	public Account(long userId, String number, long typeId, long statusId, String created, Float balance, String type) {
		super(userId);
		this.number = number;
		this.typeId = typeId;
		this.statusId = statusId;
		this.created = created;
		this.type = type;
		this.balance = balance;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
