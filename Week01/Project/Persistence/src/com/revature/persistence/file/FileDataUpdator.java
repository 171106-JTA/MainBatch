package com.revature.persistence.file;

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
	
	private static int updateUser(User user) {
		FieldParams cnds = new FieldParams();
		FieldParams values = new FieldParams();
		
		// Set condition (primary key)
		cnds.put("id", Long.toString(user.getId()));
		
		return updateUser(cnds, values);
	}
	
	private static int updateUser(FieldParams cnds, FieldParams values) {
		return 0;
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
