package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.User;
import com.revature.dao.DAOBusinessObject;

public class GetUser {
	
	public static User getUserById(Integer id) {
		List<User> list = findUserData(new User(id, null,null,null));
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public static User getUserByRoleId(Integer roleId) {
		List<User> list = findUserData(new User(null, roleId, null, null));
		return list.size() > 0 ? list.get(0) : null;
	}

	public static User getUserByUsername(String username) {
		List<User> list = findUserData(new User(null, null, username, null));
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public static User getUserByPassword(String password) {
		List<User> list = findUserData(new User(null, null, null, password));
		return list.size() > 0 ? list.get(0) : null;
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private static List<User> findUserData(User user) {
		List<User> users = new ArrayList<>();
		
		for (BusinessObject record : DAOBusinessObject.load(user))
			users.add((User)record);
		
		return users;
	}
}
