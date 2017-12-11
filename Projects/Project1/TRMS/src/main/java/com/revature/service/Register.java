package com.revature.service;

import javax.servlet.http.HttpServletRequest;

import com.revature.beans.Employee;
import com.revature.dao.EmployeeDao;
import static com.revature.log.LogUtil.log;

/*
 * checks if user exists for Registration Servlet
 */
public class Register {
	public static boolean RegisterUser(HttpServletRequest request){
		EmployeeDao ed = new EmployeeDao();
		Employee em = new Employee(
				request.getParameter("fname"),
				request.getParameter("lname"),
				request.getParameter("usrnm"),
				request.getParameter("pswrd"),
				request.getParameter("email"),
				request.getParameter("phone"),
				request.getParameter("dept")
				);
			
		
		if(!(ed.getEmployeeByUsername(request.getParameter("usrnm"))==null)){
			log.info("User already exists, cannot register under this name.");
			return false;
		}
		ed.insertEmployee(em);
		return true;
	}
}
