package com.revature.businessobject.info.account;

public class Credit extends Account {
	public static final String TOTAL = "total";
	public static final String INTEREST = "insterest";
	public static final String CREDITLIMIT = "creditlimit";
	
	private float total;
	private float interest;
	private float creditLimit;
	
	/**
	 * Initializes Credit account
	 * @param userId unique identifier (primary key)
	 * @param number account
	 * @param total amount owed 
	 * @param interest 
	 * @param creditLimit
	 */
	public Credit(long userId, long number, float total, float interest, float creditLimit, AccountStatus status) {
		super(userId, number, AccountType.CREDIT, status);
		this.total = total;
		this.interest = interest;
		this.creditLimit = creditLimit;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}


	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}


	public float getCreditLimit() {
		return creditLimit;
	}


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
