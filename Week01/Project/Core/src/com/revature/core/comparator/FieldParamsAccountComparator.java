package com.revature.core.comparator;

import java.util.Comparator;

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
	 * @return 0 if match successful else -1
	 */
	@Override
	public int compare(Object left, Object right) {
		int result = -1;
		
		if (left instanceof FieldParams) {
			if (right instanceof Checking)
				return checkingComparator.compare(left, right);
			
			if (right instanceof Credit)
				return creditComparator.compare(left, right);
		}
		
		return result;
	}
}
