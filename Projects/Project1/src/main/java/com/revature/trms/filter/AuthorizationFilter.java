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
		// TODO Auto-generated method stub
		// place your code here
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(!request.getParameter("username").equals("admin")) {
			out.print("<h3 style='color:red; background-color:pink'>INCORRECT USER</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
		else if(!request.getParameter("password").equals("passwd")) {
			out.print("<h3 style='color:red; background-color:pink'>INCORRECT password</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}else {

			// pass the request along the filter chain
			chain.doFilter(request, response); // sends it to where it was supposed to go in the first place 
		}

		// pass the request along the filter chain
		chain.doFilter(request, response); // sends it to where it was supposed to go in the first place 
	}
	// filter can be set to grab anything that goes to a specific servlet and filters it 

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
