package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Headers
 */
public class Headers extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Enumeration headers = request.getHeaderNames();
		
		out.print("<table border='1px'><tr><th>HEADER NAMES</th><th>HEADER VALUES</th></tr>");
		String header;
		while(headers.hasMoreElements()){
			header = (String)headers.nextElement();
			out.println("<tr><td>" + header + "</td><td>" + request.getHeader(header) + "</td></tr>");
		}
		out.print("</table>");
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
