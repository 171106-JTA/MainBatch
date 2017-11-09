package com.peterson.bowsercastle;

/**
 * Enum class holding 4 possible roles for a member of Bowser Castle along with said role's salary.
 * @author Alex Peterson
 */
public enum Role {

	UNVERIFIED(0),
	MEMBER(5),
	ADMIN(20),
	KING(100);
	
	private final int salary; //salary of member
	
	Role(final int value) {
		this.salary = value;
	}
	
	public int getSalary() {
		return salary;
	}
}