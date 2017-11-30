package com.revature.service;

import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.UserInfo;
import com.revature.dao.DAOBusinessObject;

public class GetUserInfo {
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
