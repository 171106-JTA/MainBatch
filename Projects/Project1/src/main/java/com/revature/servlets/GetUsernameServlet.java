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
		System.out.println("GetUsernameServlet");
		
		HttpSession session = request.getSession();
		String fname = (String) session.getAttribute("fname");
		String lname = (String) session.getAttribute("lname");
		String title = (String) session.getAttribute("title");
		String username = (String) session.getAttribute("username");
		
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String myXml = "<root>";

		myXml += "<fname>" + fname + "</fname>";
		myXml += "<lname>" + lname + "</lname>";
		myXml += "<title>" + title + "</title>";
		myXml += "<username>" + username + "</username>";
		myXml += "</root>";

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