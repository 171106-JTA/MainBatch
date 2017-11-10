package com.revature.persistence.file;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Account;
import com.revature.businessobject.info.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;

public abstract class FileDataInserter extends FileDataQuery {

	@Override
	public int insert(BusinessObject businessObject) {
		String clazz = businessObject.getClass().getSimpleName();
		
		switch (clazz.toLowerCase()) {
			case "user":
			case "admin":
			case "customer":
				return addUser((User)businessObject);
			case "userinfo":
				return addUserInfo((UserInfo)businessObject);
			case "account":
				return addAccount((Account)businessObject);
			default:
				return 0;
		}
	}

	@Override
	public int insert(String name, FieldParams values) {
		switch (name.toLowerCase()) {
			case "user":
				return addUser((User)factory.getBusinessObject(name, values));
			case "userinfo":
				return addUserInfo((UserInfo)factory.getBusinessObject(name, values));
			case "account":
				return addAccount((Account)factory.getBusinessObject(name, values));
			default:
				return 0;
		}
	}

	
	///
	// INSERT HELPER METHODS 
	///
		
	/**
	 * Adds new user 
	 * @param user new user instance 
	 * @return 1 if user was added else -1
	 */
	private static int addUser(User user) {
		// If failed to generate user
		if (user == null)
			return -1;
			
		users.add(user);
		saveUserData();
		return 1;
	}
		
	/**
	 * Adds new user info
	 * @param info new user info data 
	 * @return 1 if added else -1
	 */
	private static int addUserInfo(UserInfo info) {
		// If failed to generate user
		if (info == null)
			return -1;
			
		userInfo.add(info);
		saveUserInfoData();
		return 1;
	}
		
	/**
	 * Creates new account record
	 * @param acct new account data
	 * @return 1 if added else -1
	 */
	private static int addAccount(Account acct) {
		// If failed to generate user
		if (acct == null)
			return -1;
			
		accounts.add(acct);
		saveAccountData();
		return 1;
	}
}
