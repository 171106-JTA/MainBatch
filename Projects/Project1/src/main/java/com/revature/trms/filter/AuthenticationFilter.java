package com.revature.trms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthenticationFilter implements Filter {
	public void destroy() {
		System.out.println("Filter destroyed");
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Filter Triggered");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// if username is wrong
		if (!request.getParameter("username").equals("rightUser")) {
			out.println("<h3 tyle='color:red'>INCORRECT USER</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		// if pw is wrong
		else if (!request.getParameter("password").equals("r")) {
			out.println("<h3 style='color:red'>INCORRECT PASSWORD</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		// if authentication is correct call chain.doFilter and pass in req and response
		else {
			// chaining to the next element where order is defined in web.xml
			// The last element of the chain is target resource/servlet
			// not that it is calling for chain so it is not recursive
			chain.doFilter(request, response);
		}
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}