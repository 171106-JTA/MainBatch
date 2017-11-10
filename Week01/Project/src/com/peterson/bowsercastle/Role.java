package com.peterson.bowsercastle;

/**
 * Enum class holding 4 possible roles for a member of Bowser Castle along with said role's salary.
 * @author Alex Peterson
 */
public enum Role {

	LOCKED(-5),
	UNVERIFIED(0),
	MEMBER(10),
	ADMIN(25),
	KING(100);
	
	private final int value; //amount of stars a member earns
	
	Role(final int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}