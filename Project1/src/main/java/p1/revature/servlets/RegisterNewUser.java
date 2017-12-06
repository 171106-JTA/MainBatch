package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import p1.revature.services.RegisterUser;

@WebServlet("/RegisterNewUser/*")
public class RegisterNewUser extends BaseServlet {
	public static final String BASE = "",
			REGISTER           = "RegisterNewUser",
			INITIALIZE_EMPLOYEE= "createBaseEmployee",
			INIT_EMPLOYEE_ACCT = "createAcctCredentials",
			TEST_REQUEST_PARAMS= "testRequestParams";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get the requested service
		String requestedService = getRequestedService(req);
		String json = "";
		switch(requestedService)
		{
			case BASE:
			case REGISTER:
				int empsCreated = RegisterUser.register(req);
				if (empsCreated == 0)
				{
					System.out.println("There was a problem registering this user");
					json = "{ \"message\" : \"User registration failed\" }";
					res.setStatus(401);
				}
				else
				{
					System.out.println("User successfully registered!");
					// send redirect url to the client
					json = "{ \"url\" : \"index.html\" }";
//					RegisterUser.
					res.setStatus(200);
				}
				break;
			case TEST_REQUEST_PARAMS:
				// output the request parameters here
				java.util.Enumeration params = req.getParameterNames();
				while (params.hasMoreElements())
				{
					Object nextElement = params.nextElement();
					System.out.printf("Parameter: %s == %s\n", nextElement.toString(),
							req.getParameter(nextElement.toString()));
				}
		}
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
//		res.
		PrintWriter out = res.getWriter();
		
		out.print(json);
		out.flush();
	}

}
