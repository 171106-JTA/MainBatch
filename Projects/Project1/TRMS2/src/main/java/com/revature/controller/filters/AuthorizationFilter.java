package com.revature.controller.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
public class AuthorizationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("FILTER TRIGGERED");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(!request.getParameter("username").equals("bobbert")){
			out.println("<h3 style='color:red;background-color:pink'>INCORRECT USER</h3>");
			RequestDispatcher rd =request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}else if(!request.getParameter("password").equals("watermelon")){
			out.println("<h3 style='color:red;background-color:pink'>INCORRECT PASSWORD</h3>");
			RequestDispatcher rd =request.getRequestDispatcher("index.html");
			rd.include(request, response);			
		}else{
			//Pass request on to either the next filter (should there be more)
			//or pass it on to it's intended destination.
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
