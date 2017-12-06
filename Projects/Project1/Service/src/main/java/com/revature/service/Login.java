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
	
	/**
	 * Acquire user information from database
	 * @param user - valid user instance 
	 * @return Information pertaining to user 
	 */
	public static UserInfo getUserInfo(User user) {
		List<BusinessObject> records;
		UserInfo info = null;
		
		// If we have a valid user handle
		if (user != null) {
			// Prepare request
			info = new UserInfo();
			info.setUserId(user.getId());
			
			// Did we acquire user information from user data?
			if ((records = DAOBusinessObject.load(info)).size() == 1) 
				info = (UserInfo)records.get(0);
			else
				info = null;
		}
		
		return info;
	}
	
}
