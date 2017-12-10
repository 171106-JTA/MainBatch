package com.revature.auth;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.logging.LoggingService;

public class AuthorizeEmployee {
	
	/**
	 * Checks if user's email & password match the database
	 * @param 
	 * request - The HttpServletRequest with the login info ("email" and "password").
	 * @return 
	 * id - an int containing the logged-in employee's id (returns -1 on failure) 
	 * Also, the attribute "employeeId" will contain employee's id
	 */
	public int login(HttpServletRequest request) {
		int id = 0;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		boolean validUser = login(email, password);
		System.out.println(validUser ? "Logging In" : "Login Failed");
		
		if(validUser) {
			request.getSession(true);
			try {
				id = new EmployeeDaoImpl().getEmployee(email).getId();
				request.getSession(true);
				request.getSession().setAttribute("employeeId", id);
				LoggingService.getLogger().info("Employee " + id + " logged in.");
				return id;
				
			} catch (SQLException e) {
				e.printStackTrace();
				LoggingService.getLogger().warn("Exception getting employee "
						+ "id in AuthorizeEmployee.login(HttpServletRequest)",
						e);
				
			} catch (NullPointerException e) {
				e.printStackTrace();
				LoggingService.getLogger().warn("Couldn't find employee "
						+ "with email " + email, e);
			}
		}else {
			LoggingService.getLogger().info("Invalid login attempt for " + email);
			id = -1;
		}
		return id;
	}
	
	
	public boolean login(String email, String password) {
		EmployeeDao dao = new EmployeeDaoImpl();
		String passwordInDb = null;
		try {
			passwordInDb = dao.getEmployeePassword(email);

			if(password.equals(passwordInDb)) {
				System.out.println("Password verified.");
				//zero out password

				//found user with matching password
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LoggingService.getLogger().warn("Exception in AuthorizeEmployee.login()", e);
		}
		//couldn't find user with matching password
		return false;
	}
	
	
	
	
}
			

