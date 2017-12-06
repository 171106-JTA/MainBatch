package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import p1.revature.services.HandleEmployeeData;

@WebServlet("/login")
public class Login extends BaseServlet {
	public static final String CHECK_SESSION = "checkSession",
			AUTHENTICATE = "auth";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// if the user is already logged in, we're done
		// TODO : implement check for existing user session
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
					"redirect_url", "dashboard.html");
				// set status to OK
				res.setStatus(HttpServletResponse.SC_OK);
			}
			else
			{
				System.out.println("Invalid user login");
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
			// send the user back to where they came from!
			// TODO: implement feature to get the origin URL/URI and pass that to the returned JSON
		}

		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
	}
	
}
