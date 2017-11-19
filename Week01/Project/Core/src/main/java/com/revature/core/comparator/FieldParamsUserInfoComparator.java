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
			if (fieldParams.containsKey(UserInfo.SSN)) 
				result = fieldParams.get(UserInfo.SSN).compareTo(Long.toString(info.getSsn()));
			
			if (fieldParams.containsKey(UserInfo.USERID)) 
				result = fieldParams.get(UserInfo.USERID).compareTo(Long.toString(info.getUserId()));
			
			if (result == 0 && fieldParams.containsKey(UserInfo.EMAIL)) 
				result = fieldParams.get(UserInfo.EMAIL).compareTo(info.getEmail());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.PHONENUMBER)) 
				result = fieldParams.get(UserInfo.PHONENUMBER).compareTo(info.getPhonenumber());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.FIRSTNAME)) 
				result = fieldParams.get(UserInfo.FIRSTNAME).compareTo(info.getFirstname());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.LASTNAME)) 
				result = fieldParams.get(UserInfo.LASTNAME).compareTo(info.getLastname());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.ADDRESS1)) 
				result = fieldParams.get(UserInfo.ADDRESS1).compareTo(info.getAddress1());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.ADDRESS2)) 
				result = fieldParams.get(UserInfo.ADDRESS2).compareTo(info.getAddress2());
			
			if (result == 0 && fieldParams.containsKey(UserInfo.POSTALCODE)) 
				result = fieldParams.get(UserInfo.POSTALCODE).compareTo(info.getPostalcode());
			
			if (fieldParams.containsKey(UserInfo.STATECITYID)) 
				result = fieldParams.get(UserInfo.STATECITYID).compareTo(Long.toString(info.getStateCityId()));
			
			if (fieldParams.containsKey(UserInfo.ROLEID)) 
				result = fieldParams.get(UserInfo.ROLEID).compareTo(Long.toString(info.getRoleId()));
			
			if (fieldParams.containsKey(UserInfo.STATUSID)) 
				result = fieldParams.get(UserInfo.STATUSID).compareTo(Long.toString(info.getStatusId()));
		}
		
		return result;
	}
}
