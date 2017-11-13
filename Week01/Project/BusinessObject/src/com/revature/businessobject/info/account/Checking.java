package com.revature.businessobject.info.account;

/**
 * Allows user to deposit and withdraw funds from bank 
 * with this type of account
 * @author Antony Lulciuc
 */
public class Checking extends Account {
	public static final String TOTAL = "total";
	
	// Total amount of funds checking account has available 
	private float total;
	
	/**
	 * Initialize checking account instance 
	 * @param userId (FORIEGN KEY)
	 * @param number (primary key)
	 * @param total 
	 * @param status
	 */
	public Checking(long userId, long number, float total, String status) {
		super(userId, number, Type.CHECKING, status);
		this.total = total;
	}
	
	/**
	 * @return available funds for account
	 */
	public float getTotal() {
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		Checking other = (Checking) obj;
		if (Float.floatToIntBits(total) != Float.floatToIntBits(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Checking [total=" + total + ", " + super.toString() + "]";
	}
	
	
}
