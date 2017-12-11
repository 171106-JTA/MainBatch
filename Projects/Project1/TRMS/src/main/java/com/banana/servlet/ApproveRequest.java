package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.service.ApproveReimbursement;
import com.banana.service.UpdateEmployeeAmount;

/**
 * Servlet implementation class ApproveRequest
 */
public class ApproveRequest extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * For submitting approval data
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("AdminPage.html");
		
		if(!session.isNew()) {
			int roleId = (Integer)session.getAttribute("role");
			
			int rrId = Integer.parseInt(request.getParameter("rrId"));
			int decision = Integer.parseInt(request.getParameter("decision"));
			int empId = (Integer)session.getAttribute("empId");
			
			if(ApproveReimbursement.approve(roleId, rrId, decision, empId)) {
				out.println("Changed");
			}
			else {
				out.println("Unchanged");
			}
			
			rd.include(request, response);
		}
	}

}
