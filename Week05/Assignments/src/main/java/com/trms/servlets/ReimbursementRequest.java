package com.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trms.obj.ReimbRequest;
import com.trms.services.Services;

/**
 * Servlet implementation class ReimbursementRequest
 */
public class ReimbursementRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReimbRequest trr = new ReimbRequest(
				(String)request.getSession().getAttribute("username"),
				new Timestamp(Integer.parseInt(request.getParameter("EventYear")),
						Integer.parseInt(request.getParameter("EventMonth")),
						Integer.parseInt(request.getParameter("EventDay")),
						Integer.parseInt(request.getParameter("eventTime")),
						0,0,0),
				request.getParameter("eventName"),
				request.getParameter("eventType"),
				request.getParameter("description"),
				request.getParameter("eventAddr"),
				request.getParameter("justification"),
				Double.parseDouble(request.getParameter("costUnmodified")),
				request.getParameter("workHoursToMiss"));
		System.out.println(trr);
		
		Services.submitReimbursementRequestForm(trr); 
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h2>Request Sent</h2>");
		out.println("<a href = 'home.html'>Back</a>"); 
	}

}
