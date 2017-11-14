package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.user.UserInfo;
import com.revature.core.FieldParams;

/**
 * Used to compare user info against field data
 * @author Antony Lulciuc
 */
public class FieldParamsUserInfoComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of User
	 * @see FieldParams
	 * @see UserInfo
	 * @return 0 if match successful else non-zero
	 */
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof UserInfo) {
			FieldParams fieldParams = (FieldParams)left;
			UserInfo info = (UserInfo)right;
			
			// Set result value 
			result = 0;
			
			// Perform tests
			if (fieldParams.containsKey(UserInfo.USERID)) 
				result = fieldParams.get(UserInfo.USERID).compareTo(Long.toString(info.getUserId()));
			
			if (result == 0 && fieldParams.containsKey(UserInfo.EMAIL)) 
				result = fieldParams.get(UserInfo.EMAIL).compareTo(info.getEmail());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.PHONENUMBER)) 
				result = fieldParams.get(UserInfo.PHONENUMBER).compareTo(info.getPhonenumber());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.ADDRESS)) 
				result = fieldParams.get(UserInfo.ADDRESS).compareTo(info.getAddress());
		}
		
		return result;
	}
}
