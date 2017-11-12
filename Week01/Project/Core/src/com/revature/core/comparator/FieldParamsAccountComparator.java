package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.core.FieldParams;

public class FieldParamsAccountComparator implements Comparator<Object>{
	private static FieldParamsCheckingComparator checkingComparator = new FieldParamsCheckingComparator();
	private static FieldParamsCreditComparator creditComparator = new FieldParamsCreditComparator();
	
	/**
	 * @param left instance of FieldParams
	 * @param right instance of Checking/Credit
	 * @see FieldParams
	 * @see Checking
	 * @see Credit
	 * @return 0 if match successful else non-zero
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams) {
			if (right instanceof Checking)
				return checkingComparator.compare(left, right);
			
			if (right instanceof Credit)
				return creditComparator.compare(left, right);
			
			if (right instanceof Account) {
				FieldParams fieldParams = (FieldParams)left;
				Account account = (Account)right;
				
				// Set result value 
				result = 0;
				
				if (fieldParams.containsKey(Account.USERID)) 
					result = fieldParams.get(Account.USERID).compareTo(Long.toString(account.getUserId()));
				
				if (result == 0 && fieldParams.containsKey(Account.NUMBER)) 
					result = fieldParams.get(Account.NUMBER).compareTo(Long.toString(account.getNumber()));
				
				if (result == 0 && fieldParams.containsKey(Account.TYPE)) 
					result = fieldParams.get(Account.TYPE).compareTo(Integer.toString(account.getType().ordinal()));
			}
		}
		
		return result;
	}
}
