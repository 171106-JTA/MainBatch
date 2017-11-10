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
	 * @return 0 if match successful else -1
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof Checking) {
			FieldParams fieldParams = (FieldParams)left;
			Checking account = (Checking)right;
			
			// Set result value 
			result = 0;
			
			if (fieldParams.containsKey("userid")) 
				result = fieldParams.get("userid").equals(Long.toString(account.getUserId())) ? 0 : -1;
			
			if (fieldParams.containsKey("number")) 
				result = fieldParams.get("number").equals(Long.toString(account.getNumber())) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey("total")) 
				result = fieldParams.get("total").equals(Float.toString(account.getTotal())) ? 0 : -1;
			
			if (result != -1 && fieldParams.containsKey("type")) 
				result = fieldParams.get("type").equals(Integer.toString(account.getType().ordinal())) ? 0 : -1;
		}
		
		return result;
	}

	
}
