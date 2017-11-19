package com.revature.businessobject.info.account;

/**
 * Allows user to deposit and withdraw funds from bank 
 * with this type of account
 * @author Antony Lulciuc
 */
public class Checking extends Account {
	public static final String BALANCE = "balance";
	
	// Total amount of funds checking account has available 
	private float balance;
	
	/**
	 * Initialize checking account instance 
	 * @param userId (FORIEGN KEY)
	 * @param number (primary key)
	 * @param balance 
	 * @param status
	 */
	public Checking(long userId, long number, float balance, String status) {
		super(userId, number, Type.CHECKING, status);
		this.balance = balance;
	}
	
	/**
	 * @return available funds for account
	 */
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(balance);
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
		Checking other = (Checking) obj;
		if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Checking [total=" + balance + ", " + super.toString() + "]";
	}
	
	
}
