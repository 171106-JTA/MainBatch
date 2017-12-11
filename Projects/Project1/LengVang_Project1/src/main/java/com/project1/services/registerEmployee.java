package com.project1.services;

import javax.servlet.http.HttpServletRequest;

import com.project1.classes.Employee;
import com.project1.dao.EmployeeDao;

public class registerEmployee {
	public static boolean registerEmp(HttpServletRequest request) {
		Employee currEmp = new Employee((String)request.getParameter("employeeID"), (String)request.getParameter("password"),
				(String)request.getParameter("fname"), (String)request.getParameter("mname"), 
				(String)request.getParameter("lname"), (String)request.getParameter("email"));
		currEmp.setEmpPos(0);
		System.out.println(currEmp);
		EmployeeDao empDao = new EmployeeDao();
		
		if(empDao.empExist(currEmp) > 0) {
			return false;
		}
		
		empDao.createEmp(currEmp);
		
		
		
		
		return true;
	}

}
