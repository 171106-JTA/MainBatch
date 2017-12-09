package com.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trms.obj.ReimbRequest;
import com.trms.services.Services;

/**
 * Servlet implementation class ViewRequests
 */
public class ViewRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRequests() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession(); 
		List<ReimbRequest> requests = Services.getRequests((String) session.getAttribute("username")); 
		System.out.println("Requests:");
		out.println("<table border='2px'>");
		out.println("<tr><th>EVENT</th><th>Date of Event</th><th>Location</th><th>Category</th><th>Description</th><th>GradingFormat</th>"
				+ "<th>Reason for Attending</th><th>Anticipated Work Missed</th><th>Supervisor Approval</th><th>Departmental Approval</th><th>BenCo Approval</th>");
		for(ReimbRequest trr : requests) {
			System.out.println(trr);
			out.println("<tr>");
			out.println("<td>" + trr.getEventName() + "</td>");
			out.println("<td>" + trr.getDateAndTime() + "</td>");
			out.println("<td>" + trr.getLocation() + "</td>");
			out.println("<td>" + trr.getEventType() + "</td>");
			out.println("<td>" + trr.getDescription() + "</td>");
			out.println("<td>" + trr.getGradingFormat() + "</td>");
			out.println("<td>" + trr.getJustification() + "</td>");
			out.println("<td>" + trr.getWorkHoursMissing() + "</td>");
			out.println("<td>" + (trr.isSupervisorApproved() ? "Y" : "N") + "</td>");
			out.println("<td>" + (trr.isDeptHeadApproved() ? "Y" : "N") + "</td>");
			out.println("<td>" + (trr.isBennCoApproved() ? "Y" : "N") + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");
					
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
