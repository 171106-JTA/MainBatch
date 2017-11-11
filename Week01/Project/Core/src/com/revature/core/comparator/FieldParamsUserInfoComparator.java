package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.user.UserInfo;
import com.revature.core.FieldParams;

public class FieldParamsUserInfoComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of User
	 * @see FieldParams
	 * @see UserInfo
	 * @return 0 if match successful else -1
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof UserInfo) {
			FieldParams fieldParams = (FieldParams)left;
			UserInfo info = (UserInfo)right;
			
			// Set result value 
			result = 0;
			
			if (fieldParams.containsKey(UserInfo.USERID)) 
				result = fieldParams.get(UserInfo.USERID).equals(Long.toString(info.getUserId())) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(UserInfo.EMAIL)) 
				result = fieldParams.get(UserInfo.EMAIL).equals(info.getEmail()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(UserInfo.PHONENUMBER)) 
				result = fieldParams.get(UserInfo.PHONENUMBER).equals(info.getPhonenumber()) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey(UserInfo.ADDRESS)) 
				result = fieldParams.get(UserInfo.ADDRESS).equals(info.getAddress()) ? 0 : -1;
		}
		
		return result;
	}
}
