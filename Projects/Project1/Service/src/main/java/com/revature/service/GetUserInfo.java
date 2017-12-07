package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.businessobject.view.UserView;
import com.revature.dao.DAOBusinessObject;

public class GetUserInfo {
	
	public static List<UserView> getUserViews(String role) {
		List<BusinessObject> records = DAOBusinessObject.loadAll(User.class);
		List<UserView> views = new ArrayList<>();
		UserView temp;
		UserView view;
		UserInfo info;
		User user;
		
		// Build view list
		for (BusinessObject record : records) {
			user = (User)record;
			temp = getUserViewById(user.getId());
			
			if (!role.toUpperCase().equals("EMPLOYEE")) 
				view = temp;
			else {
				view = new UserView();
				view.setUsername(temp.getUsername());
				view.setFirstName(temp.getFirstName());
				view.setEmail(temp.getEmail());
				view.setRole(temp.getRole());
			}
		
			views.add(view);
		}
		
		return views;
	}
	
	public static UserView getUserViewById(Integer userId) {
		UserView view = new UserView();
		List<BusinessObject> records = null;
		User user = new User();
		CodeList data;
		String clause;

		// set args for request
		user.setId(userId);

		// Ensure record found
		if ((records = DAOBusinessObject.load(user)).size() > 0) {
			// cache user
			user = (User) records.get(0);

			// set user data for view
			view.setUsername(user.getUsername());

			// get User role information
			clause = "WHERE id=" + user.getRoleId();

			// Ensure valid role record
			if ((records = DAOBusinessObject.load(CodeList.class, clause)).size() == 1) {
				data = (CodeList) records.get(0);
				view.setRole(data.getValue());
			}

			// Assigns UserInfo and Department data to view 
			setUserViewData(view, userId);
		}

		return view;
	}

	public static List<BusinessObject> getUserDataByUserId(Integer userId) {
		User user = new User();
		List<BusinessObject> records;

		user.setId(userId);

		if ((records = DAOBusinessObject.load(user)).size() == 1) {
			// Strip sensitive data
			user = ((User) records.get(0));
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
			info = (UserInfo) records.get(0);

		return info;
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
	
	///
	// PRIVATE METHODS
	///

	private static void setUserViewData(UserView view, Integer userId) {
		List<BusinessObject> records;
		UserInfo info;
		String clause;
		CodeList data;

		// Ensure UserInfo data found
		if ((info = getUserInfoByUserId(userId)) != null) {
			// set info data for view
			view.setFirstName(info.getFirstName());
			view.setLastName(info.getLastName());
			view.setEmail(info.getEmail());
			view.setAddress(info.getAddress());
			view.setPhoneNumber(info.getPhoneNumber());

			clause = "WHERE (code='CITY-CODE' AND value IN (SELECT description FROM CODE_LIST WHERE id="
					+ info.getStateCityId() + ")) OR "
					+ "  (code='US-STATE' AND value IN (SELECT value FROM CODE_LIST WHERE id="
					+ info.getStateCityId() + "))";

			// Get city and state values
			if ((records = DAOBusinessObject.load(CodeList.class, clause))
					.size() == 2) {
				for (BusinessObject item : records) {
					data = (CodeList) item;

					if (data.getCode().equals("US-STATE"))
						view.setState(data.getDescription());
					else
						view.setCity(data.getDescription());
				}
			}
			
			// Get department data 
			setUserViewDepartmentData(view, userId);
		}
	}
	
	private static void setUserViewDepartmentData(UserView view, Integer userId) {
		List<BusinessObject> records;
		CodeList data; 
		String clause;
		
		// Build where clause based on role
		switch (view.getRole()) {
			case "EMPLOYEE":
				clause = "WHERE id IN (SELECT departmentid FROM COMPANY_EMPLOYEE WHERE firstname='"+ view.getFirstName() +"' AND lastname='" + view.getLastName() + "')";
				break;
			case "BENEFIT-COORDINATOR":
				clause = "WHERE id in (SELECT departmentid FROM EAR_BENEFIT_COORDINATOR WHERE bencoid=" + userId + ")"; 
				break;
			case "HEAD-SUPERVISOR":
				clause = "WHERE id=-9999";
				break;
			case "SUPERVISOR":
				clause = "WHERE id=-9999";
				break;
			default:
				clause = "WHERE id=-9999";
				break;
		}
		
		
		// Attempt to find CodeList with department descriptor 
		if ((records = DAOBusinessObject.load(CodeList.class, clause)).size() == 1) {
			data = (CodeList)records.get(0);
			view.setDepartment(data.getValue());
		}
	}
}
