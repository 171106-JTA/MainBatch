package p1.revature.services;

import javax.servlet.http.HttpServletRequest;

import p1.revature.beans.Bean;
import p1.revature.beans.Employee;
import p1.revature.dao.DepartmentDao;
import p1.revature.dao.DepartmentRoleDao;
import p1.revature.dao.EmployeeDao;

public class HandleEmployeeData extends ObjectJSONService {

	public static boolean validateUserRegistration(HttpServletRequest req)
	{
		// get everything that was sent here
		String firstName   = req.getParameter("firstName").trim(),
				lastName   = req.getParameter("lastName").trim(),
				department = req.getParameter("dept").trim(),
				deptRole   = req.getParameter("deptRole").trim(),
				managerName= req.getParameter("manager").trim(),
				userName   = req.getParameter("email").trim(),
				password   = req.getParameter("password").trim(),
				confirmPass= req.getParameter("confirmPassword").trim();
		// do validation on everything
		// firstName,lastName should not be empty
		if ((firstName.isEmpty()) || (lastName.isEmpty()))
		{
			return false;
		}
		// deptRole should not be empty
		if (deptRole.isEmpty())
		{
			return false;
		}
		// password should equal confirmPass
		if (!password.equals(confirmPass))
		{
			return false;
		}
		return true;
	}
	
	public static boolean validate(HttpServletRequest req)
	{
		// get username,password
		String username = req.getParameter("user").trim(),
				password= req.getParameter("pass").trim();
		
		return new EmployeeDao().validLoginCredentials(new Employee(username, password));
	}
}
