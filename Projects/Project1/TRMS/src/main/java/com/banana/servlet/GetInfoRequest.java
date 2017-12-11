package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.service.GetEmployeeData;
import com.banana.service.InfoRequestManipulation;

/**
 * Servlet implementation class InfoRequest
 */
public class GetInfoRequest extends HttpServlet {
	
	/**
	 * Get Info Requests:
	 * Specific for employee
	 * All for admin
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int requesteeId = (Integer)session.getAttribute("empId");
		String who = request.getParameter("from");
	
		if(!session.isNew()) {
			if(who.equals("emp")) {
				InfoRequestManipulation.getInfoRequests(response, requesteeId);
			}
			else {
				InfoRequestManipulation.getAllInfoRequests(response);
			}
		}
		
	}

	/**
	 * Inserts a Info Request into database
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int rrId = Integer.parseInt(request.getParameter("rrId"));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = request.getRequestDispatcher("AdminPage.html");
	
		if(!session.isNew()) {
			InfoRequestManipulation.insert(rrId);
			out.println("Info Requested");
		}
		
		rd.include(request, response);
	}

}
