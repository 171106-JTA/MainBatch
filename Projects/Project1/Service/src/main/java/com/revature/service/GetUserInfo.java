package com.revature.service;

import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.businessobject.view.UserView;
import com.revature.dao.DAOBusinessObject;

public class GetUserInfo {
	public static UserView getUserViewById(Integer userId) {
		UserView view = new UserView();
		List<BusinessObject> records = null;
		User user = new User();
		CodeList data;
		UserInfo info;
		String clause;
		
		// set args for request
		user.setId(userId);
		
		// Ensure record found
		if ((records = DAOBusinessObject.load(user)).size() > 0) {
			// cache user
			user = (User)records.get(0);
			
			// set user data for view
			view.setUsername(user.getUsername());
			
			// get User role information 
			clause = "WHERE id=" + user.getRoleId();
			
			// Ensure valid role record
			if ((records = DAOBusinessObject.load(CodeList.class, clause)).size() == 1) {
				data = (CodeList)records.get(0);
				view.setRole(data.getValue());
			}
			
			// Ensure UserInfo data found 
			if ((info = getUserInfoByUserId(userId)) != null) {
				// set info data for view
				view.setFirstName(info.getFirstName());
				view.setLastName(info.getLastName());
				view.setEmail(info.getEmail());
				view.setAddress(info.getAddress());
				view.setPhoneNumber(info.getPhoneNumber());
				
				clause = "WHERE (code='CITY-CODE' AND value IN (SELECT description FROM CODE_LIST WHERE id=" + info.getStateCityId() +")) OR " +
						 "  (code='US-STATE' AND value IN (SELECT value FROM CODE_LIST WHERE id=" + info.getStateCityId() + "))";
				
				// Get city and state values 
				if ((records = DAOBusinessObject.load(CodeList.class, clause)).size() == 2) {
					for (BusinessObject item : records) {
						data = (CodeList)item;
						
						if (data.getCode().equals("US-STATE"))
							view.setState(data.getDescription());
						else 
							view.setCity(data.getDescription());
					}
				}
			}	
		}
		
		return view;
	}
	
	
	public static List<BusinessObject> getUserDataByUserId(Integer userId) {
		User user = new User();
		List<BusinessObject> records;
		
		user.setId(userId);
		
		if ((records = DAOBusinessObject.load(user)).size() == 1) { 
			// Strip sensitive data 
			user = ((User)records.get(0));
			user.setPassword(null);
			user.setId(null);
			
			records.add(getUserInfoByUserId(userId));
		}
		
		return records;
	}
	
	public static UserInfo getUserInfoByUserId(Integer userId) {
		UserInfo info = new UserInfo();
		List<BusinessObject> records;
		
		info.setUserId(userId);
		
		if ((records = DAOBusinessObject.load(info)).size() != 1) 
			info = null;
		else 
			info = (UserInfo)records.get(0);
		
		return info;
	}
}
