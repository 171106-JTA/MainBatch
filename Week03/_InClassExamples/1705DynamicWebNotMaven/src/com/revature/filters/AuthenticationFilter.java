package com.revature.filters;

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
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
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
			out.println("<h3 style='color:red'>INCORRECT USER</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);	
		}else if(!request.getParameter("password").equals("bobbert")){
			out.println("<h3 style='color:red'>INCORRECT PASSWORD</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);				
		}else{
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
