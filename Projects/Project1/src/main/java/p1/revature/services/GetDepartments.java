package p1.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import p1.revature.beans.Department;
import p1.revature.beans.DepartmentRole;
import p1.revature.dao.DepartmentDao;
import p1.revature.dao.DepartmentRoleDao;

public class GetDepartments extends ObjectJSONService {
	public static List<Department> fetchDepartments(HttpServletRequest req)
	{
		return new DepartmentDao().getAllDepartments();
	}
	
	public static Department fetchDepartment(HttpServletRequest req)
	{
		// get the Department name from the request
		String deptName = req.getParameter("department");
		// return the Department object, if it exists
		return new DepartmentDao().getDepartmentByName(deptName);
	}
	
	public static List<DepartmentRole> fetchDepartmentRoles(HttpServletRequest req)
	{
		// first, we fetchDepartment
		Department dept = fetchDepartment(req);
		// next we use that Department to get DepartmentRoles
		return new DepartmentRoleDao().getDepartmentRolesByName(dept.getDepartmentName());
	}
	
}
