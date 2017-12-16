package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import p1.revature.services.GetDepartments;

@WebServlet("/FetchDepartments/*")
public class FetchDepartments extends BaseServlet {

	public static final String BASE = "FetchDepartments",
		DEPARTMENTS      = "departments",
		DEPARTMENT_ROLES = "departmentRoles";
	@Override	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String json = "";
		res.setContentType("application/json");
		// get the last parameter of url
		String serviceRequested = super.getRequestedService(req);
		switch(serviceRequested)
		{
			case "":
			case BASE:
			case DEPARTMENTS:
				json = GetDepartments.toJSON(GetDepartments.fetchDepartments(req));
				break;
			case DEPARTMENT_ROLES:
				// need to pass in the managers for the department role the user has
				json = GetDepartments.toJSON(GetDepartments.fetchDepartmentRoles(req)); 
				break;
			default:
				json = "{error: 'invalid url'}";
		}
		PrintWriter out = res.getWriter();
		out.println(json);
	}

	
	
}
