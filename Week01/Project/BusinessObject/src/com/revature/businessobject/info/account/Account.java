package com.revature.businessobject.info.account;

import com.revature.businessobject.info.Info;

public class Account extends Info {
	private long number;
	private AccountType type;

	/**
	 * Initialize basic account data 
	 * @param userId user unique identifier (primary key)
	 * @param number account number 
	 * @param type what kind of account is it
	 * @see AccountType 
	 */
	public Account(long userId, long number, AccountType type) {
		super(userId);
		this.number = number;
		this.type = type;
	}

	public long getNumber() {
		return number;
	}

	public AccountType getType() {
		return type;
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
		return true;
	}
}
