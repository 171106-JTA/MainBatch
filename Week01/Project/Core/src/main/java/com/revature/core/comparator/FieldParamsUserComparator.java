package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;

/**
 * Used to compare users against field data
 * @author Antony Lulciuc
 */
public class FieldParamsUserComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of User
	 * @see FieldParams
	 * @see User
	 * @return 0 if match successful else non-zero
	 */
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof User) {
			FieldParams fieldParams = (FieldParams)left;
			User user = (User)right;
			
			// Set result value 
			result = 0;
			
			// Perform tests
			if (fieldParams.containsKey(User.ID)) 
				result = fieldParams.get(User.ID).compareTo(Long.toString(user.getId()));
			
			if (result == 0 && fieldParams.containsKey(User.USERNAME)) 
				result = fieldParams.get(User.USERNAME).compareTo(user.getUsername());
			
			if (result == 0 && fieldParams.containsKey(User.PASSWORD)) 
				result = fieldParams.get(User.PASSWORD).compareTo(user.getPassword());
			
			if (result == 0 && fieldParams.containsKey(User.CHECKPOINT)) 
				result = fieldParams.get(User.CHECKPOINT).compareTo(user.getCheckpoint());
		}
		
		return result;
	}

}