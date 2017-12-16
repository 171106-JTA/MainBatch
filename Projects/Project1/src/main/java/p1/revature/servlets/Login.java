package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import p1.revature.beans.Employee;
import p1.revature.dao.EmployeeDao;
import p1.revature.services.HandleEmployeeData;

@WebServlet("/login")
public class Login extends BaseServlet {
	public static final String CHECK_SESSION = "checkSession",
			AUTHENTICATE = "auth";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// if the user is already logged in, we're done
		HttpSession session  = req.getSession();
		boolean isNewSession = session.isNew();
		
		String json = "";
		int loginAttempts = 0;
		if (isNewSession)
		{
			System.out.println("Created new session");
			// do user validation
			if (HandleEmployeeData.validate(req))
			{
				System.out.println("User authenticated");
				// set the redirect url to the dashboard
				json = String.format("{\"%s\" : \"%s\","
						+ "\"%s\" : \"%s\"}", 
					"authenticated", "true",
					"redirect_url", "TRMS_Dashboard.html");
				// set status to OK
				res.setStatus(HttpServletResponse.SC_OK);
				// pre-fetch data to put into the session
				fetchDataIntoSession(session, req.getParameter("user"));
			}
			else
			{
				System.out.println("Invalid user login");
				session.invalidate();
				//implement lockout feature
				json = String.format("{\"%s\" : \"%s\","
						+ "\"%s\" : \"%d\"}", 
					"authenticated", "false",
					"login_attempts", loginAttempts++);
				
				res.setStatus(401);
			}
		}
		else
		{
			System.out.println("User has already been signed in");
			// send the user back to where they came from!
			// TODO: implement feature to get the origin URL/URI and pass that to the returned JSON
			json = String.format("{\"%s\" : \"%s\"}", "redirect_url", "TRMS_Dashboard.html");
			res.setStatus(200);
			// pre-fetch data to put into the session
			fetchDataIntoSession(session, req.getParameter("user"));
		}

		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
	}
	
	public void fetchDataIntoSession(HttpSession session, String email)
	{
		EmployeeDao empDao = new EmployeeDao();
		// get the Employee for the current session
		Employee user = empDao.getEmployeeByEmail(email);
		session.setAttribute("user", user);
		// find out if user is a manager and store that in session
		boolean userIsManager = empDao.isManager(user);
		session.setAttribute("userIsManager", userIsManager);
		// TODO: handle fetching of any reimbursement requests the user might have
	}
}
