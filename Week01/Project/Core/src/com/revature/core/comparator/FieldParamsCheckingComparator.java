package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Checking;
import com.revature.core.FieldParams;

public class FieldParamsCheckingComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of Checking
	 * @see FieldParams
	 * @see Checking
	 * @return 0 if match successful else non-zero
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof Checking) {
			FieldParams fieldParams = (FieldParams)left;
			Checking account = (Checking)right;
			
			// Set result value 
			result = 0;
			
			if (fieldParams.containsKey(Checking.USERID)) 
				result = fieldParams.get(Checking.USERID).compareTo(Long.toString(account.getUserId()));
			
			if (result == 0 && fieldParams.containsKey(Checking.NUMBER)) 
				result = fieldParams.get(Checking.NUMBER).compareTo(Long.toString(account.getNumber()));
			
			if (result == 0 && fieldParams.containsKey(Checking.TOTAL)) 
				result = fieldParams.get(Checking.TOTAL).compareTo(Float.toString(account.getTotal()));
			
			if (result == 0 && fieldParams.containsKey(Checking.TYPE)) 
				result = fieldParams.get(Checking.TYPE).compareTo(account.getType());
			
			if (result == 0 && fieldParams.containsKey(Checking.STATUS)) 
				result = fieldParams.get(Checking.STATUS).compareTo(account.getStatus());
		}
		
		return result;
	}

	
}
