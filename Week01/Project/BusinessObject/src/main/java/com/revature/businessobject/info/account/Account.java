package com.revature.businessobject.info.account;

import com.revature.businessobject.info.Info;

public class Account extends Info {
	public static final String NUMBER = "account_number";
	public static final String TYPEID = "type_id";
	public static final String STATUSID = "status_id";
	public static final String CREATED = "created";
	
	private long number;
	private String type;
	private String status;
	
	/**
	 * Initialize basic account data 
	 * @param userId user unique identifier (foreign key)
	 * @param number account number 
	 * @param type what kind of account is it
	 * @see Type 
	 */
	public Account(long userId, long number, String type, String status) {
		super(userId);
		this.number = number;
		this.type = type;
		this.status = status;
	}

	/**
	 * @return account number 
	 */
	public long getNumber() {
		return number;
	}

	/**
	 * @see Type
	 * @return type of account
	 */
	public String getType() {
		return type;
	}

	/**
	 * @see Status
	 * @return is the account active/pending/blocked?
	 */
	public String getStatus() {
		return status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (number ^ (number >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (number != other.number)
			return false;
		if (type != other.type)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + ", type=" + type + ", status="
				+ status + ", " + super.toString() + "]";
	}
}