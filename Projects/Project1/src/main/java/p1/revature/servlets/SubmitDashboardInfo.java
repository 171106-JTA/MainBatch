package p1.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import p1.revature.services.HandleDashboardData;

@WebServlet("/SubmitDashboardInfo/*")
public class SubmitDashboardInfo extends BaseServlet {
	public static final String GRADES = "grades",
			REIMBURSEMENT = "reimbursementRequest"
			
	;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get the requested service
		String requestedService = getRequestedService(req);
		String json = "";
		switch(requestedService)
		{
			case GRADES:
				
				// TODO : implement this
				break;
			case REIMBURSEMENT:
				System.out.println("Processing dashboard data...");
				if (HandleDashboardData.submitReimbursementRequest(req) > 0)
				{
					json = String.format(
							"{\"form_submission_status\" : \"valid\", \"reimbursement_request\" : %s}",
							HandleDashboardData.gson.toJson(req.getSession().getAttribute("rr")));
					res.setStatus(200);
				}
				else
				{
					json = String.format(
							"{\"form_submission_status\" : \"invalid\", \"error_message\": %s}",
							"Invalid tuition reimbursement form. Please check the fields and try again.");
					res.setStatus(400);
				}
				break;
		}
		res.setContentType("application/json");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = res.getWriter();
		out.print(json);
		out.flush();
	}
	
}
