package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteCookie
 */
public class DeleteCookie extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		int count = 0;
		if(cookies==null){
			out.println("No cookies to eat!");
		}
		else{
			for(Cookie cookie: cookies){
				cookie.setMaxAge(0); //By setting age to 0, we delete our cookie.
				response.addCookie(cookie);
				count++;
			}
			out.println(count + " cookie(s) deleted!");
		}
		
		
		
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
