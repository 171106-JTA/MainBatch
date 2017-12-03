package com.revature.businessobject;

/**
 * Interface used to generalize DAO layer
 * @author Antony Lulciuc
 */
public interface BusinessObject {

	/**
	 * Ensure that the string is valid before setting
	 * @param str - what to validate 
	 * @return original string if o.k. else null
	 */
	public static String validateString(String str) {
		return (str != null && str.length() > 0) ? str : null;
	}
}
