package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class DenyReimbursementForm
 */
public class DenyReimbursementForm extends HttpServlet {
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
		System.out.println("Inside DenyReimbursementForm");
		
		String reason_for_denial = request.getParameter("denial_reason");
		
		HttpSession session = request.getSession();
		int reimbursementID = Integer.parseInt((String)session.getAttribute("ReimbursementID"));
		
		System.out.println("reason_for_denial: " + reason_for_denial);
		System.out.println("reimbursementID: " + reimbursementID);
		
		TrmsDaoImplement dao = new TrmsDaoImplement();
		dao.denyReimbursement(reimbursementID, reason_for_denial);
		
		RequestDispatcher rd = request.getRequestDispatcher("denial_success.html");
		rd.forward(request, response);
	}
}
