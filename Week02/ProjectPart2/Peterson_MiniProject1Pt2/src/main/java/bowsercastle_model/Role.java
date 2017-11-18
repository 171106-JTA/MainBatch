package bowsercastle_model;

/**
 * Enum class holding 5 possible roles for a member of Bowser Castle.
 * @author Alex Peterson
 */
public enum Role {

	LOCKED(0, 10, "LOCKED"),
	UNVERIFIED(1, 10, "UNVERIFIED"),
	MEMBER(2, 10, "MEMBER"),
	ADMIN(3, 10, "ADMIN"),
	KING(4, 1000, "KING");
	
	private final int value; //rank used for ordering roles
	private final int level; //role's level at start of creation of user
	private final String name;
	
	
	Role(final int value, final int level, final String name) {
		this.value = value;
		this.level = level;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getLevel() {
		return level;
	}
	
	/**
	 * Return the role associated with a given String representation
	 * @param name
	 * @return
	 */
	public static Role getRole(String name) {
		Role role = null;
		name = name.toUpperCase();
		if (name.equals(LOCKED.name)) {
			role = LOCKED;
		} else if (name.equals(UNVERIFIED.name)){
			role = UNVERIFIED;
		} else if (name.equals(MEMBER.name)){
			role = MEMBER;
		} else if (name.equals(KING.name)){
			role = KING;
		} else if (name.equals(ADMIN.name)){
			role = ADMIN;
		}
		
		return role;
	}
}