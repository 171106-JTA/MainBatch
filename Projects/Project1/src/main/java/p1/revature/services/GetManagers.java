package p1.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import p1.revature.beans.Department;
import p1.revature.beans.Employee;
import p1.revature.dao.EmployeeDao;

public class GetManagers extends ObjectJSONService {
	/**
	 * Gets managers for the user under the department passed in from the request
	 * @param req : HttpServletRequest containing the user's credentials
	 * @return a list of managers in the logged in user's department
	 */
	public static List<Employee> getManagersFor(HttpServletRequest req)
	{
		// try to get the department from the department parameter of req
		String dept = req.getParameter("department");
		// if it's null, get it from session of req
		if (dept == null) dept = ((Employee)req.getSession().getAttribute("user")).getDepartmentName();
		return new EmployeeDao().getManagersByDepartment(new Department(dept));
	}
	
	/**
	 * Gets the manager of the user logged in
	 * @param req : HttpServletRequest containing the user's credentials
	 * @return the manager of the employee currently logged on
	 */
	public static Employee getManagerFor(HttpServletRequest req)
	{
		// try to get the user from the active session
		HttpSession session = req.getSession();
		// if the session is new, there's something wrong here. 
		if (session.isNew()) return null;
		return new EmployeeDao().getManagerOf((Employee)session.getAttribute("user"));
	}
	
	public static List<Employee> getAllManagers()
	{
		return new EmployeeDao().getManagers();
	}
}
