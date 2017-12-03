package com.revature.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.BenefitCoordinator;
import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.dao.DAOBusinessObject;
import com.revature.service.util.ServiceUtil;

public class UpdateUser {
	/**
	 * Used to update own account (not other accounts)
	 * @param request - client request
	 * @return number of records updated 
	 */
	public static int updateMyAccount(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Integer userId;
		int total = 0;
		
		// if we have a valid session handle
		if (session != null) {
			// Get user Id
			userId = (Integer)session.getAttribute("id");
			
			// What they all have in common to update
			total = updateUser(request, userId);
			total += updateUserInfo(request, userId);
			
			// Perform extra updates (if necessary).
			if (request.getAttribute("role").equals("BENEFIT-COORDINATOR")) 
				total += updateBenefitCoordinator(request, userId);
		}
		
		return total;
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Update user record
	 * @param request
	 * @param userId
	 * @return
	 */
	private static int updateUser(HttpServletRequest request, Integer userId) {
		User newValues = new User(null, null, request.getParameter("username"), request.getParameter("password"));
		User toUpdate = new User(userId, null, null, null);
		
		return DAOBusinessObject.update(toUpdate, newValues);
	}
	
	/**
	 * Update UserInfo record
	 * @param request
	 * @param userId
	 * @return
	 */
	private static int updateUserInfo(HttpServletRequest request, Integer userId) {
		List<BusinessObject> records;
		UserInfo toUpdate = new UserInfo();
		UserInfo newValues = new UserInfo();
		CodeList stateCity;
		String value;
		
		// Prepare data
		toUpdate.setUserId(userId);
		
		// new data for user
		newValues.setFirstName(request.getParameter("firstname"));
		newValues.setLastName(request.getParameter("lastname"));
		newValues.setAddress(request.getParameter("address"));
		newValues.setPhoneNumber(request.getParameter("phonenumber"));
		
		// if Email
		if ((value = (String)request.getAttribute("email")) != null && ServiceUtil.validateEmail(value))
			newValues.setEmail(value);
		
		// If has state city information
		if (request.getParameter("state") != null && request.getParameter("city") != null) {
			stateCity = new CodeList(null, "CITY-CODE-GROUP", request.getParameter("state"), request.getParameter("city"));
			records = DAOBusinessObject.load(stateCity);
			
			// if state city code group found 
			if (records.size() == 1) {
				stateCity = (CodeList)records.get(0);
				newValues.setStateCityId(stateCity.getId());
			}
		}
				
		
		return DAOBusinessObject.update(toUpdate, newValues);
	}
	
	private static int updateBenefitCoordinator(HttpServletRequest request, Integer userId) {
		BenefitCoordinator toUpdate = new BenefitCoordinator(null, userId, null);
		BenefitCoordinator newValues = new BenefitCoordinator(null, null, Integer.parseInt(request.getParameter("departmentid")));
		
		return DAOBusinessObject.update(toUpdate, newValues);
	}
}
