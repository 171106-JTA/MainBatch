package com.revature.businessobject.info.account;

import java.util.Date;

/**
 * 
 * @author Antony Lulciuc
 *
 */
public class Credit extends Account {
	public static final String TOTAL = "total";
	public static final String INTEREST = "insterest";
	public static final String CREDITLIMIT = "creditlimit";
	
	private float total;
	private float interest;
	private float creditLimit;
	


	public Credit(long userId, String number, long typeId, long statusId,
			String created, float total, float interest, float creditLimit) {
		super(userId, number, typeId, statusId, created, Type.CREDIT);
		this.total = total;
		this.interest = interest;
		this.creditLimit = creditLimit;
	}

	/**
	 * @return remaining balance owed to account
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * Assigns total amount of owed to this account
	 * @param total - amount still owed to bank
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * @return Total interest applied to account every month
	 */
	public float getInterest() {
		return interest;
	}

	/**
	 * Assigns monthly interest
	 * @param interest applied eevery month
	 */
	public void setInterest(float interest) {
		this.interest = interest;
	}

	/**
	 * @return total amount user is allowed to borrow from bank on account
	 */
	public float getCreditLimit() {
		return creditLimit;
	}

	/**
	 * Set maximum amount user is allowed to borrow on account
	 * @param creditLimit - amount user is allowed to spend using this account
	 */
	public void setCreditLimit(float creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(creditLimit);
		result = prime * result + Float.floatToIntBits(interest);
		result = prime * result + Float.floatToIntBits(total);
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
		Credit other = (Credit) obj;
		if (Float.floatToIntBits(creditLimit) != Float
				.floatToIntBits(other.creditLimit))
			return false;
		if (Float.floatToIntBits(interest) != Float
				.floatToIntBits(other.interest))
			return false;
		if (Float.floatToIntBits(total) != Float.floatToIntBits(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Credit [total=" + total + ", interest=" + interest
				+ ", creditLimit=" + creditLimit + ", "+ super.toString() + "]";
	}
	
	
}
