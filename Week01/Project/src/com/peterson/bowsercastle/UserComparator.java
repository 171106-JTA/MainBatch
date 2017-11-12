package com.peterson.bowsercastle;

import java.util.Comparator;

/**
 * Comparator class used to sort our PriorityQueue by order of role.
 * King -> Admin -> Member -> Unverified -> Locked
 * @author Alex
 */
public class UserComparator implements Comparator<User>, java.io.Serializable {
	
	private static final long serialVersionUID = 1308684049795555450L;

	/**
	 * Compare the two users roles and return which user has higher role.
	 */
	@Override
	public int compare(final User user1, final User user2) {
		if (user1.getRole().getValue() > user2.getRole().getValue()) {
			return - 1;
		} else if (user1.getRole().getValue() < user2.getRole().getValue()) {
			return 1;
		}
		return 0;
	}
}