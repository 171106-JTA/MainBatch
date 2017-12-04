package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetUsernameServlet
 */
public class GetUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("we're gonna get the username");
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String myXml = "<root>";

		myXml += "<username id='username'>" + username + "</username>";
		myXml += "</root>";
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(username));

		out.println(myXml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}