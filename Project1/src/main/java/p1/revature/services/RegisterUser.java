package p1.revature.services;

import javax.servlet.http.HttpServletRequest;

import p1.revature.beans.Bean;
import p1.revature.beans.Department;
import p1.revature.beans.Employee;
import p1.revature.dao.DepartmentDao;
import p1.revature.dao.DepartmentRoleDao;
import p1.revature.dao.EmployeeDao;
import p1.revature.servlets.BaseServlet;

public class RegisterUser extends BaseServlet {
	public static int register(HttpServletRequest req)
	{
		// TODO: check that user specified by this data isn't already logged in
		// get all the parameters
		String firstName   = req.getParameter("firstName").trim(),
				lastName   = req.getParameter("lastName").trim(),
				department = req.getParameter("dept").trim(),
				deptRole   = req.getParameter("deptRole").trim(),
				managerName= req.getParameter("manager").trim(),
				userName   = req.getParameter("email").trim(),
				password   = req.getParameter("password").trim();
		int managerID = Bean.NULL;
		EmployeeDao empDao = new EmployeeDao();
		// if managerID is not defined in the request
		if ((req.getParameter("managerID") == null) || (req.getParameter("managerID").isEmpty()))
		{
			
			// pre-fetch the manager id from the database
			Employee manager = empDao.getManagerByDeptInfo(new Department(department), deptRole, managerName); 
			if (manager != null) managerID = manager.getEmployeeID();
		}
		else
		{
			managerID = new Integer(req.getParameter("managerID").trim());	// don't worry about this right now
		}
		// if user data is valid
		if (HandleEmployeeData.validateUserRegistration(req))
		{
			System.out.println("I received valid data");
			// add the user to the database
			Employee emp = new Employee(managerID,
				firstName, 
				lastName,
				Bean.NULL, // department role id
				Bean.NULL, // employee salary
				Bean.NULL, // home location id
				userName, 
				password);
			emp.setDepartmentRoleID(new DepartmentRoleDao().getDepartmentRole(
					new DepartmentDao().getDepartmentByName(department), deptRole)
				.getDepartmentID());
			return empDao.createNewEmployee(emp);
		}
		return 0;
	}
}
