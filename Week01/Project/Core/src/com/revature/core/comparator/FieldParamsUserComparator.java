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
			
			if (fieldParams.containsKey("id")) 
				result = fieldParams.get("id").equals(Long.toString(user.getId())) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey("username")) 
				result = fieldParams.get("username").equals(user.getUsername()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey("password")) 
				result = fieldParams.get("password").equals(user.getPassword()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey("role")) 
				result = fieldParams.get("role").equals(Integer.toString(user.getRole().ordinal())) ? 0 : -1;
		}
		
		return result;
	}

}
