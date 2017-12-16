package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import p1.revature.services.GetManagers;
import p1.revature.services.GetTRMSFormData;
import p1.revature.services.GetUserMetadata;

@WebServlet("/CheckDashboardState/*")
public class CheckDashboardState extends BaseServlet {
	public static final String ALL_MANAGERS = "allManagers",
			MANAGERS   = "managers",
			MANAGER    = "manager",
			USER_DATA  = "employeeMetadata",
			STATES     = "states",
			EVENT_TYPES= "expenseTypes",
			R_REQUESTS = "reimbursementRequests",
			EMPLOYEES  = "employees"
	;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String serviceRequested = getRequestedService(req);
		String json = "";
		// nobody should be seeing this screen iff they're not logged in
		switch(serviceRequested)
		{
			case ALL_MANAGERS:
				System.out.println("getting info of all managers....");
				json = String.format("{\"managers\" : %s }", 
						GetManagers.toJSON(GetManagers.getAllManagers()));
				res.setStatus(200);
				break;
			case MANAGERS:
				System.out.println("getting info of the managers in the department of the person logged on to this system");
				json = String.format("{\"managers\" : %s }", 
						GetManagers.toJSON(GetManagers.getManagersFor(req)));
				res.setStatus(200);
				break;
			case MANAGER:
				System.out.println("getting info of the manager of the logged on user");
				json = String.format("{\"manager\" : %s }",
						GetManagers.toJSON(GetManagers.getManagerFor(req)));
				res.setStatus(200);
				break;
			case USER_DATA:
				// write the user data (stored in the session) to json
				System.out.println("getting user data...");
				json = GetUserMetadata.initJSONFromSession(req); 
				res.setStatus(200);
				break;
			case STATES:	
				System.out.println("getting location states...");
				json = GetTRMSFormData.getLocationStates();
				res.setStatus(200);
				break;
			case EVENT_TYPES:
				System.out.println("getting expense types...");
				json = GetTRMSFormData.getExpenseTypes();
				res.setStatus(200);
				break;
			case R_REQUESTS:
				System.out.println("getting reimbursement requests...");
				json = GetTRMSFormData.getReimbursementRequests(req);
				res.setStatus(200);
				break;
			case EMPLOYEES:
				System.out.println("getting all employees of the logged in manager...");
				json = GetTRMSFormData.getEmployeesForManager(req);
				res.setStatus(200);
				break;
				
		}
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
	}

	
}
