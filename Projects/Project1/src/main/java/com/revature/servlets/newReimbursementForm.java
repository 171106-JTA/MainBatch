package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.bean.ReimbursementForm;
import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class newReimbursementForm
 */
public class newReimbursementForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (session.isNew()) {
			out.println("Please create a session first!");
			session.invalidate(); // Clears the session entirely
		} else {
			//Check if request comes from a user with "employee" permission
			String permission = (String)session.getAttribute("permission");
			
			if(!permission.equals("employee")) {
				out.println("Only Employees can create new Reimbursement requests");
				session.invalidate();
			} else {
				String username = (String)session.getAttribute("username");
				String date_parsed[] = request.getParameter("eventdate").split("-");
				String year = date_parsed[0]; 
				String month = date_parsed[1];
				String day = date_parsed[2];
				String []time_parsed = request.getParameter("eventtime").split(":");
				String hour = time_parsed[0]; 
				String minute = time_parsed[1];
				String description = request.getParameter("eventdescription");
				String cost = request.getParameter("eventcost");
				String gradingFormat = request.getParameter("eventGradingFormat");
				String eventType = request.getParameter("eventType");
				String street = request.getParameter("eventStreet");
				String city = request.getParameter("eventCity");
				String state = request.getParameter("eventState");
				String zip = request.getParameter("eventZip");
				
				ReimbursementForm RF = new ReimbursementForm(username, year, month, day, hour, minute, description,
						cost, gradingFormat, eventType, street, city, state, zip);
				
				TrmsDaoImplement dao = new TrmsDaoImplement();
				dao.insertNewReimbursementForm(RF);
				out.println("Reimbursement Form Submitted");
			}
		}

		out.println("<hr>" + "<a href='index.html'>BACK</a>");
	}
	
}
