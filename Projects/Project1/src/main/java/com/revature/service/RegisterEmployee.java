package com.revature.service;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Employee;
import com.revature.beans.Title;
import com.revature.dao.TRMSDao;

public class RegisterEmployee {
	
	public static boolean register(HttpServletRequest request) {
		final String username = (String) request.getAttribute("username");
		final int password = String.valueOf(request.getAttribute("password")).hashCode();
		final String fname = (String) request.getAttribute("firstname");
		final String lname = (String) request.getAttribute("lastname");
		final String address = (String) request.getAttribute("address");
		final String zipcode = (String) request.getAttribute("zipcode");
		final String city = (String) request.getAttribute("city");
		final String state = (String) request.getAttribute("state");
		
		final Employee emp = new Employee(username, password, fname, lname, Title.EMPLOYEE, address, zipcode,
									      city, state);
		
		if (TRMSDao.getEmployeeByUsername(username) == null) {
			TRMSDao dao = TRMSDao.getDao();
			dao.insertEmployee(emp);
		} else {
			//TODO inform user that the username is taken
		}
		
		
		
		return false;
	}
	

}