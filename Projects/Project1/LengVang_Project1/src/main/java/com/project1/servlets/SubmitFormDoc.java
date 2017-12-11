package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SubmitFormDoc
 */
public class SubmitFormDoc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		session.setAttribute("amount", Double.parseDouble(request.getParameter("amount")));
		session.setAttribute("RemType", request.getParameter("RemType"));
		
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		out.println("<title>Submit Document</title>");
		
		out.println("<h3>Please upload document for reimbursement.");
		out.println("<form action=\"FormRequest\" method=\"post\" enctype=\"multipart/form-data\">\r\n" + 
				"	\r\n" + 
				"		<input type=\"file\" name=\"file\" multiple>\r\n" + 
				"		<input type=\"submit\" name=\"Submit File\">\r\n" + 
				"	</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
