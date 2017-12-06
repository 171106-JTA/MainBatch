package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import p1.revature.services.GetManagers;

@WebServlet("/FetchEmployees/*")
public class FetchEmployees extends BaseServlet {

	public static final String MANAGER_NAMES_FOR_DEPARTMENT = "managers";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// parse the url for the requested service  
		String requestedService = super.getRequestedService(req);
		String json = "";
		switch(requestedService)
		{
			case MANAGER_NAMES_FOR_DEPARTMENT :
				json = GetManagers.toJSON(GetManagers.getManagersFor(req));
				break;
			// TODO: implement other url use cases here
			default:
				break;
		}
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		out.println(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO : decide on use cases for this, and handling those use cases
	}
	
}
