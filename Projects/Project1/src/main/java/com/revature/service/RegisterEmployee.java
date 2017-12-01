package com.revature.service;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Employee;
import com.revature.beans.Title;
import com.revature.dao.TRMSDao;

public class RegisterEmployee {

	public static boolean register(HttpServletRequest request) {
		final String username = (String) request.getAttribute("regUsername");
		final int password = String.valueOf(request.getAttribute("regPassword1")).hashCode();
		final String fname = (String) request.getAttribute("fname");
		final String lname = (String) request.getAttribute("lname");
		final String phoneNumber = (String)request.getAttribute("phoneNumber");
		final String address = (String) request.getAttribute("address");
		final String zipcode = (String) request.getAttribute("zipcode");
		final String city = (String) request.getAttribute("city");
		final String state = (String) request.getAttribute("state");
		
		final Employee emp = new Employee(username, password, fname, lname, phoneNumber, Title.UNVERIFIED, address, zipcode,
				city, state);

		if (TRMSDao.getEmployeeByUsername(username) == null) {
			System.out.println("We are registering a new user");
			TRMSDao dao = TRMSDao.getDao(); //grab our dao
			dao.insertEmployee(emp);
		} else {
			//TODO inform user that the username is taken
		}



		return false;
	}


}