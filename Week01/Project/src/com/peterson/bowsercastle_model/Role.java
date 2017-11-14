package com.peterson.bowsercastle_model;

/**
 * Enum class holding 5 possible roles for a member of Bowser Castle.
 * @author Alex Peterson
 */
public enum Role {

	LOCKED(0, 10),
	UNVERIFIED(1, 10),
	MEMBER(2, 10),
	ADMIN(3, 10),
	KING(4, 1000);
	
	private final int value; //rank used for ordering roles
	private final int level; //role's level at start of creation of user
	
	
	Role(final int value, final int level) {
		this.value = value;
		this.level = level;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getLevel() {
		return level;
	}
}