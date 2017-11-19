package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Checking;
import com.revature.core.FieldParams;

/**
 * Used to compare checking account against field data
 * @author Antony Lulciuc
 * @see FieldParamsAccountComparator
 */
public class FieldParamsCheckingComparator implements Comparator<Object> {

	/**
	 * @param left instance of FieldParams
	 * @param right instance of Checking
	 * @see FieldParams
	 * @see Checking
	 * @return 0 if match successful else non-zero
	 */
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof Checking) {
			FieldParams fieldParams = (FieldParams)left;
			Checking account = (Checking)right;
			
			// Set result value 
			result = 0;
			
			// perform test
			if (result == 0 && fieldParams.containsKey(Checking.BALANCE)) 
				result = fieldParams.get(Checking.BALANCE).compareTo(Float.toString(account.getBalance()));
		}
		
		return result;
	}

	
}
