package com.revature.service;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Employee;
import com.revature.beans.Title;
import com.revature.dao.TRMSDao;

public class RegisterEmployee {

	public static boolean register(HttpServletRequest request) {
		final String username = (String) request.getParameter("regUsername");
		final int password = String.valueOf(request.getParameter("regPassword1")).hashCode();
		final String fname = (String) request.getParameter("fname");
		final String lname = (String) request.getParameter("lname");
		final String phoneNumber = (String)request.getParameter("phoneNumber");
		final String address = (String) request.getParameter("address");
		final String zipcode = (String) request.getParameter("zipcode");
		final String city = (String) request.getParameter("city");
		final String state = (String) request.getParameter("state");
		
		final Employee emp = new Employee(username, password, fname, lname, phoneNumber, Title.UNVERIFIED, address, zipcode,
				city, state);
		
		final TRMSDao dao = TRMSDao.getDao();

		if (dao.getEmployeeByUsername(username) == null) {
			boolean success = dao.insertEmployee(emp);
			
			return success;
		} else {
			return false;
		}
	}
}