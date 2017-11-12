package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Credit;
import com.revature.core.FieldParams;

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
			
			if (fieldParams.containsKey(Credit.USERID)) 
				result = fieldParams.get(Credit.USERID).compareTo(Long.toString(account.getUserId()));
			
			if (result == 0 && fieldParams.containsKey(Credit.NUMBER)) 
				result = fieldParams.get(Credit.NUMBER).compareTo(Long.toString(account.getNumber()));
			
			if (result == 0 && fieldParams.containsKey(Credit.TOTAL)) 
				result = fieldParams.get(Credit.TOTAL).compareTo(Float.toString(account.getTotal()));
			
			if (result == 0 && fieldParams.containsKey(Credit.INTEREST)) 
				result = fieldParams.get(Credit.INTEREST).compareTo(Float.toString(account.getInterest()));
			
			if (result == 0 && fieldParams.containsKey(Credit.CREDITLIMIT)) 
				result = fieldParams.get(Credit.CREDITLIMIT).compareTo(Float.toString(account.getCreditLimit()));
			
			if (result == 0 && fieldParams.containsKey(Credit.TYPE)) 
				result = fieldParams.get(Credit.TYPE).compareTo(Integer.toString(account.getType().ordinal()));
		}
		
		return result;
	}
}
