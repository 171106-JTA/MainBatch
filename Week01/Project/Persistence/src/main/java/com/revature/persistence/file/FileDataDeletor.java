package com.revature.persistence.file;

import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;

/**
 * Handles delete requests 
 * @author Antony Lulciuc
 */
public abstract class FileDataDeletor extends FileDataInserter {
	/**
	 * Removes record from system
	 * @param businessObject instance representation of record to be removed
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	@Override
	public int delete(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case BusinessClass.USER:
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
				return removeUser(fieldParamsFactory.getFieldParams(businessObject));
			case BusinessClass.USERINFO:
				return removeUserInfo(fieldParamsFactory.getFieldParams(businessObject));
			case BusinessClass.ACCOUNT:
			case BusinessClass.CREDIT:
			case BusinessClass.CHECKING:
				return removeAccount(fieldParamsFactory.getFieldParams(businessObject));
			default:
				return -1;
		}
	}

	/**
	 * Removes records from system
	 * @param name data structure to remove
	 * @param cnds conditions record must have to be deleted
	 * @return if less than or equal to 0 then failed, else how many records were removed 
	 */
	@Override
	public int delete(String name, FieldParams cnds) {
		switch (name.toLowerCase()) {
			case BusinessClass.USER:
				return removeUser(cnds);
			case BusinessClass.USERINFO:
				return removeUserInfo(cnds);
			case BusinessClass.ACCOUNT:
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
		List<Integer> indices;
		Iterator<Integer> it;
		int size = users.size();
			
		// Log transaction request
		logger.debug("Attempting to remove user data");
		
		if (cnds == null) {
			logger.debug("Removing all users");
			users.clear();
		}
		else {
			indices = findAllUserIndex(cnds);
				
			if ((size = indices.size()) > 0) {
				it = indices.iterator();
					
				for (int i = 0; it.hasNext(); i++) 
					users.remove(it.next() - i);
			}
		}
		
		// Commit changes 
		if (size > 0) {
			saveUserData();
			
			// Log transaction
			logger.debug("Removed " + size + " user records");;
		}
		
		return size;
	}
		
	/**
	 * Removes user from system
	 * @param cnds what to remove
	 * @return greater than 0 if records were removed
	 */
	private static int removeUserInfo(FieldParams cnds) {
		List<Integer> indices;
		Iterator<Integer> it;
		int size = userInfo.size();
			
		// Log transaction request
		logger.debug("Attempting to remove userinfo data");
		
		if (cnds == null) {
			logger.debug("Removing all userinfo");
			userInfo.clear();
		}
		else {
			indices = findAllUserInfoIndex(cnds);
				
			if ((size = indices.size()) > 0) {
				it = indices.iterator();
					
				for (int i = 0; it.hasNext(); i++) 
					userInfo.remove(it.next() - i);
			}
		}
		
		// Commit changes 
		if (size > 0) {
			saveUserInfoData();
			
			// Log transaction
			logger.debug("Removed " + size + " userinfo records");;
		}
		
		return size;
	}
		
	/**
	 * Removes account from system
	 * @param cnds what to remove
	 * @return greater than 0 if records were removed
	 */
	private static int removeAccount(FieldParams cnds) {
		List<Integer> indices;
		Iterator<Integer> it;
		int size = accounts.size();
			
		// Log transaction request
		logger.debug("Attempting to remove account data");
		
		if (cnds == null) {
			logger.debug("Removing all accounts");
			accounts.clear();
		}
		else {
			indices = findAllAccountIndex(cnds);
				
			if ((size = indices.size()) > 0) {
				it = indices.iterator();
					
				for (int i = 0; it.hasNext(); i++) 
					accounts.remove(it.next() - i);
			}
		}
		
		// Commit changes 
		if (size > 0) {
			saveAccountData();
			
			// Log transaction
			logger.debug("Removed " + size + " account records");;
		}
		
		return size;
	}
}