package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TrainerLogin
 */
public class TrainerLogin extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
//		response.sendRedirect("Hello.do");
		response.setContentType("text");
		HttpSession session = request.getSession();
		session.setAttribute("success?", "Yes");
		session.setAttribute("visit", 0);
		session.setAttribute("username", "test");
		PrintWriter out = response.getWriter();
		out.println("CUSTOM ERROR");
		
		
		
/*		RequestDispatcher rd = request.getRequestDispatcher("http://www.google.com");
		rd.forward(request, response);*/
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
