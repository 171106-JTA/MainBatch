package com.revature.businessobject.info.account;

public class Checking extends Account {
	public static final String TOTAL = "total";
	
	private float total;
	
	public Checking(long userId, long number, float total, String status) {
		super(userId, number, Type.CHECKING, status);
		this.total = total;
	}
	
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
