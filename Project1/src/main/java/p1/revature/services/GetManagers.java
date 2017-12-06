package p1.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import p1.revature.beans.Department;
import p1.revature.beans.Employee;
import p1.revature.dao.EmployeeDao;

public class GetManagers extends ObjectJSONService {
	public static List<Employee> getManagersFor(HttpServletRequest req)
	{
		
		return new EmployeeDao().getManagersByDepartment(new Department(req.getParameter("department")));
	}
}
