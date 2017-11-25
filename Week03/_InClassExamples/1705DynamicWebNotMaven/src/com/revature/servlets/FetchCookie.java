package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FetchCookie
 */
public class FetchCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = null;
		cookies = request.getCookies();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<table><tr><th>Cookie Name</th><th>Cookie Value</th></tr>");
		
		if(!(cookies==null)){
			for(Cookie cookie : cookies){
				out.println("<tr><td>" + cookie.getName() + "</td><td>" + cookie.getValue() + "</td></tr>");
			}
		}
		
		out.println("</table>");
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
