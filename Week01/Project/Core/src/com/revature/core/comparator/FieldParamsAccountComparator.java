package com.revature.core.comparator;

import java.util.Comparator;

import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.core.FieldParams;

/**
 * Used to compare accounts against field data
 * @author Antony Lulciuc
 */
public class FieldParamsAccountComparator implements Comparator<Object>{
	private static FieldParamsCheckingComparator checkingComparator = new FieldParamsCheckingComparator();
	private static FieldParamsCreditComparator creditComparator = new FieldParamsCreditComparator();
	
	/**
	 * @param left instance of FieldParams
	 * @param right instance of Checking/Credit
	 * @see FieldParams -
	 * @see Checking
	 * @see Credit
	 * @return 0 if match successful else non-zero
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		// If field params supplied
		if (left instanceof FieldParams) {
			// If account supplied 
			if (right instanceof Account) {
				FieldParams fieldParams = (FieldParams)left;
				Account account = (Account)right;
				
				// Set result value 
				result = 0;
				
				// Perform comparisons
				if (fieldParams.containsKey(Account.USERID)) 
					result = fieldParams.get(Account.USERID).compareTo(Long.toString(account.getUserId()));
				
				if (result == 0 && fieldParams.containsKey(Account.NUMBER)) 
					result = fieldParams.get(Account.NUMBER).compareTo(Long.toString(account.getNumber()));
				
				if (result == 0 && fieldParams.containsKey(Account.TYPE)) 
					result = fieldParams.get(Account.TYPE).compareTo(account.getType());
				
				if (result == 0 && fieldParams.containsKey(Account.STATUS)) 
					result = fieldParams.get(Account.STATUS).compareTo(account.getStatus());
				
				// If checking account
				if (result == 0 && right instanceof Checking)
					return checkingComparator.compare(left, right);
				
				// If credit account
				if (result == 0 && right instanceof Credit)
					return creditComparator.compare(left, right);
			}
		}
		
		return result;
	}
}
