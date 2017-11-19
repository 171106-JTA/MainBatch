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
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams && right instanceof Credit) {
			FieldParams fieldParams = (FieldParams)left;
			Credit account = (Credit)right;
			
			// Set result value 
			result = 0;
			
			// Perform tests
			if (result == 0 && fieldParams.containsKey(Credit.BALANCE)) 
				result = fieldParams.get(Credit.BALANCE).compareTo(Float.toString(account.getBalance()));
			
			if (result == 0 && fieldParams.containsKey(Credit.MINIMALPAYMENTDUE)) 
				result = fieldParams.get(Credit.MINIMALPAYMENTDUE).compareTo(Float.toString(account.getMinimalPaymentDue()));
			
			if (result == 0 && fieldParams.containsKey(Credit.CREDITLIMIT)) 
				result = fieldParams.get(Credit.CREDITLIMIT).compareTo(Float.toString(account.getCreditLimit()));
			
			if (result == 0 && fieldParams.containsKey(Credit.RATEID)) 
				result = fieldParams.get(Credit.RATEID).compareTo(Long.toString(account.getRateId()));
		}
		
		return result;
	}
}
