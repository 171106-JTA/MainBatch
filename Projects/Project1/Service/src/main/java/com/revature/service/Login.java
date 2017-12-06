package com.revature.service;

import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.dao.DAOBusinessObject;
import com.revature.service.util.ServiceUtil;

public class Login {
	
	/**
	 * Attempt to login to account 
	 * @param username - name of account
	 * @param password - password associated with account
	 * @return user account
	 */
	public static User login(String username, String password) {
		List<BusinessObject> records;
		User account = null;
		
		// Login attempt must have user name and password
		if (!ServiceUtil.isNullOrEmpty(username) && !ServiceUtil.isNullOrEmpty(password)) {
			records = DAOBusinessObject.load(new User(null,null, username, password));
			
			// check if user found
			if (records.size() == 1)
				account = (User)records.get(0);
		}
		
		return account;
	}
}
