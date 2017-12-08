package com.trms.servlets;

import java.io.IOException;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReimbRequest trr = new ReimbRequest(
				(String)request.getSession().getAttribute("username"),
				request.getParameter("EventMonth") + "-" + request.getParameter("EventDay") + "-" + request.getParameter("EventYear"),
				request.getParameter("eventTime"),
				request.getParameter("eventName"),
				request.getParameter("eventType"),
				request.getParameter("description"),
				request.getParameter("justification"),
				Double.parseDouble(request.getParameter("costUnmodified")),
				request.getParameter("workHoursToMiss"));
		System.out.println(trr);
		
		Services.submitReimbursementRequestForm(trr); 
				
				
				
				
				
		
	}

}
