package com.revature.ear;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.businessobject.BusinessObject;
import com.revature.service.GetUserInfo;
import com.revature.service.GetWidget;
import com.revature.service.util.ServiceUtil;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		RequestDispatcher rq;

		// If no session found
		if (session == null)
			response.sendError(500);
		else {
			// If user did not just logged into account
			if (!session.getAttribute("status").equals("login")) 
				handleDashboardRequest(request, response, session);
			else {
				// update status
				session.setAttribute("status", "ok");
				
				// If user logged into account 
				rq = request.getRequestDispatcher("./resource/html/dashboard.html");
				rq.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	///
	//	PRIVATE METHODS 
	///
	
	private void handleDashboardRequest(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		String transtype = request.getParameter("transtype");
		List<BusinessObject> records = new ArrayList<>();
		PrintWriter out = null;
		String result = null;
		RequestDispatcher rd = null;
		String msg = "ok";
		
		try {
			// Attempt to process request 
			switch (transtype.toUpperCase()) {
				case "GETUSERINFO":
					response.setContentType("text/json");
					result = ServiceUtil.toJson(GetUserInfo.getUserDataByUserId((Integer)session.getAttribute("id")));
					break;
				case "GETWIDGET":
					response.setContentType("text/html");
				    result = GetWidget.getWidget(request.getParameter("widget"));
					break;
				default:
					response.setContentType("text/html");
					msg = "Error in Dashboard, where transtype=[\'" + transtype + "\'] is unknown";
			}
		} catch (Exception e) {
			 msg = "Error in Dashboard, where transtype=[\'" + transtype + "\'] with result=[\'" + e.getMessage() + "\']";
		}
		
		// Set content type
		out = response.getWriter();
		
		// save result 
		out.print(result);
		
		// Save request status message 
		session.setAttribute("status", msg);
	}
	
	
	
}
