package com.revature.persistence.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.comparator.FieldParamsUserComparator;

public abstract class FileDataDeletor extends FileDataInserter {

	@Override
	public int delete(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case "user":
			case "admin":
			case "customer":
				return 0;
			case "userinfo": 
				return 0;
			case "account": 
				return 0;
			default:
				return -1;
		}
	}

	@Override
	public int delete(String name, FieldParams cnds) {
		switch (name.toLowerCase()) {
			case "user":
				return removeUser(cnds);
			case "userinfo": 
				return removeUserInfo(cnds);
			case "account": 
				return removeAccount(cnds);
			default:
				return -1;
		}
	}
	
	///
	// DELETE HELPER METHODS 
	///
		
	/**
	 * Removes user from system
	 * @param cnds what to remove
	 * @return greater than 0 if records were removed
	 */
	private static int removeUser(FieldParams cnds) {
		FieldParamsUserComparator comparator = new FieldParamsUserComparator();
		List<Integer> indices = new ArrayList<>();
		Iterator<User> userIt = users.iterator();
		Iterator<Integer> foundIt;
		int size = users.size();
			
		// Log transaction request
		logger.debug("Attempting to remove user data");
			
		for (int i = 0; userIt.hasNext(); i++) {
			if (comparator.compare(cnds, userIt.next()) == 0)
				indices.add(i);
		}
			
		if ((size = indices.size()) > 0) {
			foundIt = indices.iterator();
				
			for (int i = 0; foundIt.hasNext(); i++) 
				users.remove(foundIt.next() - i);
				
			// Commit changes 
			saveUserData();
				
			// Log transaction
			logger.debug("Removed " + size + " user records");;
		}
			
		return 0;
	}
		
	/**
	 * Removes user from system
	 * @param cnds what to remove
	 * @return greater than 0 if records were removed
	 */
	private static int removeUserInfo(FieldParams cnds) {
			
			
		return 0;
	}
		
	/**
	 * Removes account from system
	 * @param cnds what to remove
	 * @return greater than 0 if records were removed
	 */
	private static int removeAccount(FieldParams cnds) {
			
			
		return 0;
	}
}
