package com.revature.persistence.file;

import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;

public class FileDataUpdator extends FileDataDeletor {
	
	@Override
	public int update(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case "user":
				return updateUser((User)businessObject);
			case "userinfo":
				return updateUserInfo((UserInfo)businessObject);
			case "account":
				return updateAccount((Account)businessObject);
			default:
				return -1;
		}
	}

	@Override
	public int update(String name, FieldParams cnds, FieldParams values) {
		switch (name.toLowerCase()) {
			case "user":
				return updateUser(cnds, values);
			case "userinfo":
				return updateUserInfo(cnds, values);
			case "account":
				return updateAccount(cnds, values);
			default:
				return -1;
		}
	}

	///
	// PRIVATE METHODS 
	///
	
	/**
	 * Condition to update account is instance id
	 * @param user what to update 
	 * @return 1 if updated else 0
	 */
	private static int updateUser(User user) {
		FieldParams cnds = new FieldParams();
		
		// Set condition (primary key)
		cnds.put("id", Long.toString(user.getId()));
		
		return updateUser(cnds, fieldParamsFactory.getFieldParams(user));
	}
	
	/**
	 * Attempts to update all records which meet specified conditions 
	 * @param cnds what values user needs to be updated 
	 * @param values what to update
	 * @return total number of records updated
	 */
	private static int updateUser(FieldParams cnds, FieldParams values) {
		List<Integer> indices = findAllUserIndex(cnds);
		Iterator<Integer> it = indices.iterator();
		FieldParams params;
		int index;
		
		// Log update request
		logger.debug("Attempting to update user records");
		
		// Updates user data 
		while (it.hasNext()) {
			// Get position
			index = it.next();
			
			// Convert to FieldParams
			params = fieldParamsFactory.getFieldParams(users.get(index));
			
			// update values 
			for (String key : values.keySet()) 
				params.put(key, values.get(key));
			
			// update users record
			users.set(index, (User)businessObjectFactory.getBusinessObject("User", values));
		}
		
		// Commit changes 
		saveUserData();
		
		// Log commit 
		logger.debug("Updated " + indices.size() + " user records");
		
		return indices.size();
	}

	private static int updateUserInfo(UserInfo info) {
		return 0;
	}
	
	private static int updateUserInfo(FieldParams cnds, FieldParams values) {
		return 0;
	}
	
	private static int updateAccount(Account acct) {
		return 0;
	}
	
	private static int updateAccount(FieldParams cnds, FieldParams values) {
		return 0;
	}
}
