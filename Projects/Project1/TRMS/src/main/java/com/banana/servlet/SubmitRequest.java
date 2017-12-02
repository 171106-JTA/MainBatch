package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.service.InsertReimbursement;

/**
 * Servlet implementation class SubmitRequest
 */
public class SubmitRequest extends HttpServlet {
	
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
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd = null;
		
		if(InsertReimbursement.request(request, response)){
			out.println("<h1>Success</h1>");
			rd = request.getRequestDispatcher("employee.html");
			rd.include(request, response);
		}
		else {
			out.println("<h1 style:'color:red'>Fail</h1>");
			rd = request.getRequestDispatcher("employee.html");
			rd.include(request, response);
		}
	}
}
