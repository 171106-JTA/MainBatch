package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.core.FieldParams;

/**
 * Used to compare credit account against field data
 * @author Antony Lulciuc
 * @see FieldParamsAccountComparator
 */
public class FieldParamsCreditComparator implements Comparator<Object>{
	/**
	 * @param left instance of FieldParams
	 * @param right instance of Credit
	 * @see FieldParams
	 * @see Credit
	 * @return 0 if match successful else non-zero
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof Credit) {
			FieldParams fieldParams = (FieldParams)left;
			Credit account = (Credit)right;
			
			// Set result value 
			result = 0;
			
			// Perform tests
			if (result == 0 && fieldParams.containsKey(Credit.TOTAL)) 
				result = fieldParams.get(Credit.TOTAL).compareTo(Float.toString(account.getTotal()));
			
			if (result == 0 && fieldParams.containsKey(Credit.INTEREST)) 
				result = fieldParams.get(Credit.INTEREST).compareTo(Float.toString(account.getInterest()));
			
			if (result == 0 && fieldParams.containsKey(Credit.CREDITLIMIT)) 
				result = fieldParams.get(Credit.CREDITLIMIT).compareTo(Float.toString(account.getCreditLimit()));
		}
		
		return result;
	}
}
