package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;

public class FieldParamsUserComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of User
	 * @see FieldParams
	 * @see User
	 * @return 0 if match successful else -1
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof User) {
			FieldParams fieldParams = (FieldParams)left;
			User user = (User)right;
			
			// Set result value 
			result = 0;
			
			if (fieldParams.containsKey(User.ID)) 
				result = fieldParams.get(User.ID).equals(Long.toString(user.getId())) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(User.USERNAME)) 
				result = fieldParams.get(User.USERNAME).equals(user.getUsername()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(User.PASSWORD)) 
				result = fieldParams.get(User.PASSWORD).equals(user.getPassword()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(User.CHECKPOINT)) 
				result = fieldParams.get(User.CHECKPOINT).equals(Integer.toString(user.getCheckpoint().ordinal())) ? 0 : -1;
		}
		
		return result;
	}

}
