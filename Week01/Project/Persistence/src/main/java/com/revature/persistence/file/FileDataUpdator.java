package com.revature.persistence.file;

import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.FieldParams;

/**
 * Handles record update requests 
 * @author Antony Lulciuc
 */
public class FileDataUpdator extends FileDataDeletor {
	/**
	 * Updates existing record
	 * @param businessObject must have unique identifier to update record
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	@Override
	public int update(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case BusinessClass.USER:
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
				return updateUser((User)businessObject);
			case BusinessClass.USERINFO:
				return updateUserInfo((UserInfo)businessObject);
			case BusinessClass.ACCOUNT:
			case BusinessClass.CREDIT:
			case BusinessClass.CHECKING:
				return updateAccount((Account)businessObject);
			default:
				return -1;
		}
	}

	/**
	 * Updates existing records
	 * @param name data structure to update
	 * @param cnds values data must have to perform update
	 * @param values fields to update
	 * @return if less than or equal to 0 then failed, else how many records were updated 
	 */
	@Override
	public int update(String name, FieldParams cnds, FieldParams values) {
		switch (name.toLowerCase()) {
			case BusinessClass.USER:
				return updateUser(cnds, values);
			case BusinessClass.USERINFO:
				return updateUserInfo(cnds, values);
			case BusinessClass.ACCOUNT:
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
			users.set(index, (User)businessObjectFactory.getBusinessObject(BusinessClass.USER, params));
		}
		
		// Commit changes 
		saveUserData();
		
		// Log commit 
		logger.debug("Updated " + indices.size() + " user records");
		
		return indices.size();
	}

	/**
	 * Condition to update account is instance userid
	 * @param info what to update 
	 * @return 1 if updated else 0
	 */
	private static int updateUserInfo(UserInfo info) {
		FieldParams cnds = new FieldParams();
		
		// Set condition (primary key)
		cnds.put("userid", Long.toString(info.getUserId()));
		
		return updateUserInfo(cnds, fieldParamsFactory.getFieldParams(info));
	}
	
	/**
	 * Attempts to update all records which meet specified conditions 
	 * @param cnds what values userinfo needs to be updated 
	 * @param values what to update
	 * @return total number of records updated
	 */
	private static int updateUserInfo(FieldParams cnds, FieldParams values) {
		List<Integer> indices = findAllUserInfoIndex(cnds);
		Iterator<Integer> it = indices.iterator();
		FieldParams params;
		int index;
		
		// Log update request
		logger.debug("Attempting to update userinfo records");
		
		// Updates userinfo data 
		while (it.hasNext()) {
			// Get position
			index = it.next();
			
			// Convert to FieldParams
			params = fieldParamsFactory.getFieldParams(userInfo.get(index));
			
			// update values 
			for (String key : values.keySet()) 
				params.put(key, values.get(key));
			
			// update userinfo record
			userInfo.set(index, (UserInfo)businessObjectFactory.getBusinessObject(BusinessClass.USERINFO, params));
		}
		
		// Commit changes 
		saveUserInfoData();
		
		// Log commit 
		logger.debug("Updated " + indices.size() + " userinfo records");
		
		return indices.size();
	}
	
	/**
	 * Condition to update account is instance userid and number (composite key)
	 * @param acct what to update 
	 * @return 1 if updated else 0
	 */
	private static int updateAccount(Account acct) {
		FieldParams cnds = new FieldParams();
		
		// Set condition (composite key)
		cnds.put(Account.USERID, Long.toString(acct.getUserId()));
		cnds.put(Account.NUMBER, acct.getNumber());
	
		return updateAccount(cnds, fieldParamsFactory.getFieldParams(acct));
	}
	
	/**
	 * Attempts to update all records which meet specified conditions 
	 * @param cnds what values account needs to be updated 
	 * @param values what to update
	 * @return total number of records updated
	 */
	private static int updateAccount(FieldParams cnds, FieldParams values) {
		List<Integer> indices = findAllAccountIndex(cnds);
		Iterator<Integer> it = indices.iterator();
		FieldParams params;
		int index;
		
		// Log update request
		logger.debug("Attempting to update account records");
		
		// Updates account data 
		while (it.hasNext()) {
			// Get position
			index = it.next();
			
			// Convert to FieldParams
			params = fieldParamsFactory.getFieldParams(accounts.get(index));
			
			// update values 
			for (String key : values.keySet()) 
				params.put(key, values.get(key));
			
			// update account record
			accounts.set(index, (Account)businessObjectFactory.getBusinessObject(BusinessClass.ACCOUNT, params));
		}
		
		// Commit changes 
		saveAccountData();
		
		// Log commit 
		logger.debug("Updated " + indices.size() + " account records");
		
		return indices.size();
	}
}
