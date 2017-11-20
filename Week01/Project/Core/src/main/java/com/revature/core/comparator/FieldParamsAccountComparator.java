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
					result = fieldParams.get(Account.NUMBER).compareTo(account.getNumber());
				
				if (result == 0 && fieldParams.containsKey(Account.TYPEID)) 
					result = fieldParams.get(Account.TYPEID).compareTo(Long.toString(account.getTypeId()));
				
				if (result == 0 && fieldParams.containsKey(Account.STATUSID)) 
					result = fieldParams.get(Account.STATUSID).compareTo(Long.toString(account.getStatusId()));
				
				if (result == 0 && fieldParams.containsKey(Account.BALANCE)) 
					result = fieldParams.get(Account.BALANCE).compareTo(Float.toString(account.getBalance()));
				
				
				/*// If checking account
				if (result == 0 && right instanceof Checking)
					return checkingComparator.compare(left, right);
				
				// If credit account
				if (result == 0 && right instanceof Credit)
					return creditComparator.compare(left, right);*/
			}
		}
		
		return result;
	}
}
